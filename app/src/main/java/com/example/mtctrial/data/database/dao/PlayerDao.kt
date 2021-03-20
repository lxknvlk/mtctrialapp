package com.example.mtctrial.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mtctrial.data.database.model.PlayerData

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    fun getAll(): List<PlayerData>

    @Query("SELECT * FROM player")
    fun getAllLiveData(): LiveData<List<PlayerData>>

    @Query("SELECT * FROM player WHERE playerID = :playerID")
    fun findById(playerID: String): PlayerData

    @Insert
    fun insertAll(playerList: List<PlayerData>)

    @Delete
    fun delete(user: PlayerData)
}