package com.dalydays.android.room

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mDb: UserDatabase? = null

    private lateinit var mDbWorkerThread: DbWorkerThread

    private val mUiHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDbWorkerThread = DbWorkerThread("dbWorkerThread")
        mDbWorkerThread.start()

        mDb = UserDatabase.getInstance(this)

        // insert a new user
        insertUser(User(firstName = "Joe", lastName = "Miller", age = 47))

        // read the sample user from the database (which will update the UI
        fetchUser()
    }

    private fun bindDataWithUi(user: User?) {
        first_name_tv.text = user?.firstName.toString()
        last_name_tv.text = user?.lastName.toString()
        age_tv.text = user?.age.toString()
    }

    private fun fetchUser() {
        val task = Runnable {
            val allUsers = mDb?.UserDao()?.getAll()
            mUiHandler.post {
                if (allUsers == null || allUsers.isEmpty()) {
                    Toast.makeText(applicationContext, "No data found in db", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(applicationContext, "Found ${allUsers.size} user(s) in the database", Toast.LENGTH_SHORT).show()
                    bindDataWithUi(allUsers[0])
                }
            }
        }
        mDbWorkerThread.postTask(task)
    }

    private fun insertUser(user: User) {
        val task = Runnable { mDb?.UserDao()?.insert(user) }
        mDbWorkerThread.postTask(task)
    }

    override fun onDestroy() {
        UserDatabase.destroyInstance()
        mDbWorkerThread.quit()
        super.onDestroy()
    }
}
