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
import com.example.mtctrial.ui.adapter.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val filteredList: MutableList<ListElement> = mutableListOf()
    val originalPlayerList: MutableList<PlayerListElement> = mutableListOf()
    val originalTeamList: MutableList<TeamListElement> = mutableListOf()
    var currentSearchString: String = ""
    var currentPlayersList: Int = 0
    var currentTeamsList: Int = 0

    enum class SearchType(type: String){
        PLAYERS("players"),
        TEAMS("teams")
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

    val networkErrorLiveData = MutableLiveData<String>()
    val requestSpinnerLiveData = MutableLiveData<Boolean>()

    val playerLiveData: LiveData<List<PlayerListElement>> by lazy {
        Transformations.map(dataRepository.playerLiveData) { playerEntityList ->
            playerEntityList.map { playerEntity ->
                playerMapper.entityToListElement(playerEntity)
            }
        }
    }

    val teamLiveData: LiveData<List<TeamListElement>> by lazy {
        Transformations.map(dataRepository.teamLiveData) { playerEntityList ->
            playerEntityList.map { teamEntity ->
                teamMapper.entityToListElement(teamEntity)
            }
        }
    }

    fun searchData(searchString: String, searchType: SearchType?, offset: Int?) {
        viewModelScope.launch(Dispatchers.IO) {
            requestSpinnerLiveData.postValue(true)
            val responseWrapper: ApiResponseWrapper = apiClient.search(searchString, searchType, offset)

            if (responseWrapper.error != null){
                networkErrorLiveData.postValue(responseWrapper.error)
                requestSpinnerLiveData.postValue(false)
                return@launch
            }

            val searchResponse = responseWrapper.result ?: return@launch

            val playerEntities: List<PlayerEntity>? = searchResponse.players
            val teamEntities: List<TeamEntity>? = searchResponse.teams

            dataRepository.persistData(playerEntities, teamEntities)
            requestSpinnerLiveData.postValue(false)
        }
    }

    fun filterListElement(it: ListElement): Boolean {
        val playerElementMatchesQuery = it is PlayerListElement && (
                it.playerSecondName.toLowerCase().contains(currentSearchString)
                        || it.playerFirstName.toLowerCase().contains(currentSearchString)
                        || it.playerClub.toLowerCase().contains(currentSearchString)
                        || it.playerNationality.toLowerCase().contains(currentSearchString))

        val teamElementMatchesQuery = it is TeamListElement && (
                it.teamStadium.toLowerCase().contains(currentSearchString)
                        || it.teamNationality.toLowerCase().contains(currentSearchString)
                        || it.teamCity.toLowerCase().contains(currentSearchString)
                        || it.teamName.toLowerCase().contains(currentSearchString))

        val isSeparatorElement = it is SeparatorListElement
        val isSearchStringEmpty = currentSearchString.isEmpty()
        val isButtonListElement = it is ButtonListElement

        return isButtonListElement
                || isSeparatorElement
                || isSearchStringEmpty
                || playerElementMatchesQuery
                || teamElementMatchesQuery
    }
}