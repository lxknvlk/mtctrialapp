package com.example.mtctrial.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.mtctrial.data.api.ApiClient
import com.example.mtctrial.data.api.model.ApiResponseWrapper
import com.example.mtctrial.data.api.model.PlayerEntity
import com.example.mtctrial.data.api.model.SearchResponse
import com.example.mtctrial.data.api.model.TeamEntity
import com.example.mtctrial.data.database.AppDatabase
import com.example.mtctrial.data.database.mapper.PlayerMapper
import com.example.mtctrial.data.database.mapper.TeamMapper
import com.example.mtctrial.data.database.repository.DataRepository
import com.example.mtctrial.ui.adapter.PlayerListElement
import com.example.mtctrial.ui.adapter.TeamListElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

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

    val networkErrorLiveData = MutableLiveData<Boolean>()
    val requestSpinnerLiveData = MutableLiveData<Boolean>()

    val playerLiveData: LiveData<List<PlayerListElement>> by lazy {
        Transformations.map(dataRepository.playerLiveData) { playerEntityList ->
            playerEntityList.map { playerEntity ->
                PlayerListElement(
                    playerID = playerEntity.playerID,
                    playerFirstName = playerEntity.playerFirstName,
                    playerSecondName = playerEntity.playerSecondName,
                    playerNationality = playerEntity.playerNationality,
                    playerAge = playerEntity.playerAge,
                    playerClub = playerEntity.playerClub
                )
            }
        }
    }

    val teamLiveData: LiveData<List<TeamListElement>> by lazy {
        Transformations.map(dataRepository.teamLiveData) { playerEntityList ->
            playerEntityList.map { teamEntity ->
                TeamListElement(
                        teamID = teamEntity.teamID,
                        teamName = teamEntity.teamName,
                        teamStadium = teamEntity.teamStadium,
                        isNation = teamEntity.isNation,
                        teamNationality = teamEntity.teamNationality,
                        teamCity = teamEntity.teamCity
                    )
            }
        }
    }

    fun searchData(searchString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            requestSpinnerLiveData.postValue(true)
            val responseWrapper: ApiResponseWrapper = apiClient.search(searchString)

            if (responseWrapper.error != null && responseWrapper.error == "network"){
                networkErrorLiveData.postValue(true)
                requestSpinnerLiveData.postValue(false)
                return@launch
            }

            val searchResponse = responseWrapper.result ?: return@launch

            val playerEntities: List<PlayerEntity>? = searchResponse.playerEntities
            val teamEntities: List<TeamEntity>? = searchResponse.teamEntities

            dataRepository.persistData(playerEntities, teamEntities)
            requestSpinnerLiveData.postValue(false)
        }
    }
}