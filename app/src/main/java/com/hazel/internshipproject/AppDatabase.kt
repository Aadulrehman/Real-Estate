package com.hazel.internshipproject

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "my-database"
                    ).build()
            return instance!!
        }
    }

}