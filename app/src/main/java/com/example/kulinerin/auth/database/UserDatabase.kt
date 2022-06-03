package com.example.kulinerin.auth.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class UserDatabase : RoomDatabase() {
    companion object {
        @Volatile private var INSTANCE: UserDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserDatabase {
            return INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: Room.databaseBuilder(
                            context.applicationContext, UserDatabase::class.java, "story_database")
                            .fallbackToDestructiveMigration()
                            .build()
                            .also { INSTANCE = it }
                }
        }
    }
}