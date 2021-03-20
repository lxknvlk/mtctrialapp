package com.example.mtctrial.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mtctrial.data.api.ApiClient
import com.example.mtctrial.data.model.Player
import com.example.mtctrial.data.model.Team
import com.example.mtctrial.ui.adapter.ListElement
import com.example.mtctrial.ui.adapter.PlayerListElement
import com.example.mtctrial.ui.adapter.SeparatorListElement
import com.example.mtctrial.ui.adapter.TeamListElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val searchResponseLiveData = MutableLiveData<List<ListElement>>()

    private val apiClient: ApiClient by lazy { ApiClient() }

    fun searchData(searchString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val searchResponse = apiClient.search(searchString)

            val players: List<Player>? = searchResponse?.players
            val teams: List<Team>? = searchResponse?.teams

            val listElements = mutableListOf<ListElement>()

            listElements.add(SeparatorListElement("Players"))

            players?.forEach { player ->
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

            listElements.add(SeparatorListElement("Teams"))

            teams?.forEach { team ->
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
        }
    }
}