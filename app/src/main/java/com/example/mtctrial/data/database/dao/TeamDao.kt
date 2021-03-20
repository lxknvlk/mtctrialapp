package com.example.mtctrial.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.mtctrial.data.database.model.TeamData

@Dao
interface TeamDao {
    @Query("SELECT * FROM team")
    fun getAll(): List<TeamData>

    @Query("SELECT * FROM team")
    fun getAllLiveData(): LiveData<List<TeamData>>

    @Query("SELECT * FROM team WHERE teamID = :teamID")
    fun findById(teamID: String): TeamData

    @Insert
    fun insertAll(teamList: List<TeamData>)

    @Delete
    fun delete(user: TeamData)
}