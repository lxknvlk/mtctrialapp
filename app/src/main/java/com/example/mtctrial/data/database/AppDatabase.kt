package com.example.mtctrial.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mtctrial.data.database.dao.PlayerDao
import com.example.mtctrial.data.database.dao.TeamDao
import com.example.mtctrial.data.database.model.PlayerData
import com.example.mtctrial.data.database.model.TeamData

@Database(entities = [
    PlayerData::class, TeamData::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE
                ?: Room.databaseBuilder(context, AppDatabase::class.java, "app.db")
                    .enableMultiInstanceInvalidation()
                    .build()
        }
    }

    abstract fun playerDao(): PlayerDao
    abstract fun teamDao(): TeamDao
}