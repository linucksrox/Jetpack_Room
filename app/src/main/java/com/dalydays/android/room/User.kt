package com.dalydays.android.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user")
data class User(@PrimaryKey(autoGenerate = true) var id: Long?,
                @ColumnInfo(name = "first_name") var firstName: String,
                @ColumnInfo(name = "last_name") var lastName: String,
                @ColumnInfo(name = "age") var age: Int
)