package com.example.mtctrial.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.mtctrial.TrialApplication
import com.example.mtctrial.data.api.ApiClient
import com.example.mtctrial.data.api.model.ApiResponseWrapper
import com.example.mtctrial.data.api.model.PlayerEntity
import com.example.mtctrial.data.api.model.TeamEntity
import com.example.mtctrial.data.database.mapper.PlayerMapper
import com.example.mtctrial.data.database.mapper.TeamMapper
import com.example.mtctrial.data.database.repository.DataRepository
import com.example.mtctrial.ui.adapter.*
import com.example.mtctrial.utils.unaccent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel(application: Application) : AndroidViewModel(application) {

    init {
        (application as TrialApplication).appComponent.inject(this)
    }

    val originalPlayerList: MutableList<PlayerListElement> = mutableListOf()
    val originalTeamList: MutableList<TeamListElement> = mutableListOf()
    var currentSearchString: String = ""
    var currentPlayersList: Int = 0
    var currentTeamsList: Int = 0

    enum class SearchType(type: String){
        PLAYERS("players"),
        TEAMS("teams")
    }

    @Inject lateinit var apiClient: ApiClient
    @Inject lateinit var dataRepository: DataRepository
    @Inject lateinit var teamMapper: TeamMapper
    @Inject lateinit var playerMapper: PlayerMapper

    val networkErrorLiveData = MutableLiveData<Exception>()
    val requestSpinnerLiveData = MutableLiveData<Boolean>()
    val morePlayersFetchedCountLiveData = MutableLiveData<Int>()
    val moreTeamsFetchedCountLiveData = MutableLiveData<Int>()

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

            if (responseWrapper.exception != null){
                networkErrorLiveData.postValue(responseWrapper.exception)
                requestSpinnerLiveData.postValue(false)
                return@launch
            }

            val searchResponse = responseWrapper.result ?: return@launch

            val playerEntities: List<PlayerEntity>? = searchResponse.players
            val teamEntities: List<TeamEntity>? = searchResponse.teams

            when (searchType) {
                SearchType.PLAYERS -> {
                    morePlayersFetchedCountLiveData.postValue(playerEntities?.size ?: 0)
                }
                SearchType.TEAMS -> {
                    moreTeamsFetchedCountLiveData.postValue(teamEntities?.size ?: 0)
                }
                else -> {
                    moreTeamsFetchedCountLiveData.postValue(teamEntities?.size ?: 0)
                    morePlayersFetchedCountLiveData.postValue(playerEntities?.size ?: 0)
                }
            }

            dataRepository.persistData(playerEntities, teamEntities)
            requestSpinnerLiveData.postValue(false)
        }
    }

    fun filterListElement(it: ListElement): Boolean {
        val playerElementMatchesQuery = it is PlayerListElement && (
                it.playerSecondName.unaccent().contains(currentSearchString, true)
                        || it.playerFirstName.unaccent().contains(currentSearchString, true)
                        || it.playerClub.unaccent().contains(currentSearchString, true)
                        || it.playerNationality.unaccent().contains(currentSearchString, true)
                        || it.playerAge.unaccent().contains(currentSearchString, true)
                )

        val teamElementMatchesQuery = it is TeamListElement && (
                it.teamCity.unaccent().contains(currentSearchString, true)
                        || it.teamName.unaccent().contains(currentSearchString, true)
                        || it.teamNationality.unaccent().contains(currentSearchString, true)
                        || it.teamStadium.unaccent().contains(currentSearchString, true)
                )

        val isSeparatorElement = it is SeparatorListElement
        val isSearchStringEmpty = currentSearchString.isEmpty()
        val isButtonListElement = it is ButtonListElement

        return isButtonListElement
                || isSeparatorElement
                || isSearchStringEmpty
                || playerElementMatchesQuery
                || teamElementMatchesQuery
    }

    fun togglePlayerFavorite(element: PlayerListElement) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.updatePlayer(playerMapper.listElementToEntity(element.copy(isFavorite = !element.isFavorite)))
        }
    }
}