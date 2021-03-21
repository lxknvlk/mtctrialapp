package com.example.mtctrial.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mtctrial.data.database.model.PlayerData

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    fun getAll(): List<PlayerData>

    @Query("SELECT * FROM player")
    fun getAllLiveData(): LiveData<List<PlayerData>>

    @Query("SELECT * FROM player WHERE playerID = :playerID")
    fun findById(playerID: String): PlayerData

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(playerList: List<PlayerData>)

    @Update
    fun update(playerData: PlayerData)

    @Delete
    fun delete(user: PlayerData)

    @Query("SELECT * FROM player WHERE isFavorite")
    fun getAllFavoritesLiveData(): LiveData<List<PlayerData>>
}