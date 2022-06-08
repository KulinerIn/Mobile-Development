package com.example.kulinerin.food.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kulinerin.auth.database.UserDatabase

abstract class FoodDatabase : RoomDatabase() {
    companion object {
        @Volatile private var INSTANCE: FoodDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FoodDatabase {
            return INSTANCE
                ?: synchronized(this) {
                    INSTANCE
                        ?: Room.databaseBuilder(
                            context.applicationContext, FoodDatabase::class.java, "food_database")
                            .fallbackToDestructiveMigration()
                            .build()
                            .also { INSTANCE = it }
                }
        }
    }
}