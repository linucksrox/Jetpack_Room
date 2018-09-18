package com.dalydays.android.room

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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

        first_name_tv.text = "Joe"
        last_name_tv.text = "Miller"
        age_tv.text = "57"
    }
}
