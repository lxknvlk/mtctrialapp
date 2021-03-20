package com.example.mtctrial.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mtctrial.data.api.ApiClient
import com.example.mtctrial.data.api.model.PlayerEntity
import com.example.mtctrial.data.api.model.SearchResponse
import com.example.mtctrial.data.api.model.TeamEntity
import com.example.mtctrial.data.database.AppDatabase
import com.example.mtctrial.data.database.mapper.PlayerMapper
import com.example.mtctrial.data.database.mapper.TeamMapper
import com.example.mtctrial.data.database.model.PlayerData
import com.example.mtctrial.data.database.repository.DataRepository
import com.example.mtctrial.ui.adapter.ListElement
import com.example.mtctrial.ui.adapter.PlayerListElement
import com.example.mtctrial.ui.adapter.SeparatorListElement
import com.example.mtctrial.ui.adapter.TeamListElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val searchResponseLiveData: MutableLiveData<List<ListElement>> by lazy {
        val mediatorLiveData = MediatorLiveData<List<ListElement>>()

        mediatorLiveData.addSource(dataRepository.playerLiveData) { playerEntityList ->
            val listElements = mutableListOf<ListElement>()

            listElements.add(SeparatorListElement("Players"))

            playerEntityList?.forEach { player ->
                listElements.add(
                    PlayerListElement(
                        playerID = player.playerID,
                        playerFirstName = player.playerFirstName,
                        playerSecondName = player.playerSecondName,
                        playerNationality = player.playerNationality,
                        playerAge = player.playerAge,
                        playerClub = player.playerClub
                    )
                )
            }

            mediatorLiveData.postValue(listElements)
        }

        mediatorLiveData.addSource(dataRepository.teamLiveData) { teamEntityList ->
            val listElements = mutableListOf<ListElement>()
            listElements.add(SeparatorListElement("Teams"))

            teamEntityList?.forEach { team ->
                listElements.add(
                    TeamListElement(
                        teamID = team.teamID,
                        teamName = team.teamName,
                        teamStadium = team.teamStadium,
                        isNation = team.isNation,
                        teamNationality = team.teamNationality,
                        teamCity = team.teamCity
                    )
                )
            }

            mediatorLiveData.postValue(listElements)
        }

        mediatorLiveData
    }

    private val apiClient: ApiClient by lazy {
        ApiClient()
    }

    private val playerMapper: PlayerMapper by lazy {
        PlayerMapper()
    }

    private val teamMapper: TeamMapper by lazy {
        TeamMapper()
    }

    private val appDatabase: AppDatabase by lazy {
        AppDatabase.getInstance(application)
    }

    private val dataRepository: DataRepository by lazy {
        DataRepository(
            appDatabase,
            playerMapper,
            teamMapper
        )
    }

    fun searchData(searchString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val searchResponse: SearchResponse? = apiClient.search(searchString)

            val playerEntities: List<PlayerEntity>? = searchResponse?.playerEntities
            val teamEntities: List<TeamEntity>? = searchResponse?.teamEntities

            dataRepository.persistData(playerEntities, teamEntities)
        }
    }
}