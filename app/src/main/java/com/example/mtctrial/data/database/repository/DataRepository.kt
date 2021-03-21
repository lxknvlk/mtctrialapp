package com.example.mtctrial.data.database.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.example.mtctrial.data.api.model.PlayerEntity
import com.example.mtctrial.data.api.model.TeamEntity
import com.example.mtctrial.data.database.AppDatabase
import com.example.mtctrial.data.database.mapper.PlayerMapper
import com.example.mtctrial.data.database.mapper.TeamMapper
import com.example.mtctrial.data.database.model.PlayerData
import com.example.mtctrial.data.database.model.TeamData

class DataRepository(
    private val appDatabase: AppDatabase,
    private val playerMapper: PlayerMapper,
    private val teamMapper: TeamMapper
) {

    val playerLiveData: LiveData<List<PlayerEntity>> by lazy {
        Transformations.map(appDatabase.playerDao().getAllLiveData()) {
            it.map { playerMapper.dataToEntity(it) }
        }
    }

    val teamLiveData: LiveData<List<TeamEntity>> by lazy {
        Transformations.map(appDatabase.teamDao().getAllLiveData()) {
            it.map { teamMapper.dataToEntity(it) }
        }
    }

    fun persistData(players: List<PlayerEntity>?, teams: List<TeamEntity>?){
        appDatabase.runInTransaction {
            players?.let {
                val playerDataList: List<PlayerData> = players.map { playerMapper.entityToData(it) }
                appDatabase.playerDao().insertAll(playerDataList)
            }

            teams?.let {
                val teamDataList: List<TeamData> = teams.map { teamMapper.entityToData(it) }
                appDatabase.teamDao().insertAll(teamDataList)
            }
        }
    }
}