package com.example.mtctrial.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {

    companion object {
        var BaseUrl = "http://trials.mtcmobile.co.uk/api/football/1.0/"
    }

    val searchResponseLiveData = MutableLiveData<List<ListElement>>()

    init {
        val retrofit = Retrofit.Builder().baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(APIClient::class.java)
        val call = service.search("barc")

        call.enqueue(object : Callback<ApiResponseWrapper> {
            override fun onResponse(call: Call<ApiResponseWrapper>, response: Response<ApiResponseWrapper>) {
                if (response.code() == 200) {
                    val apiResponseWrapper: ApiResponseWrapper? = response.body()
                    val searchResponse: SearchResponse? = apiResponseWrapper?.result

                    val players: List<Player>? = searchResponse?.players
                    val teams: List<Team>? = searchResponse?.teams

                    val listElements = mutableListOf<ListElement>()

                    listElements.add(SeparatorListElement("Players"))

                    players?.forEach { player ->
                        listElements.add(PlayerListElement(
                            playerID = player.playerID,
                            playerFirstName = player.playerFirstName,
                            playerSecondName = player.playerSecondName,
                            playerNationality = player.playerNationality,
                            playerAge = player.playerAge,
                            playerClub = player.playerClub
                        ))
                    }

                    listElements.add(SeparatorListElement("Teams"))

                    teams?.forEach { team ->
                        listElements.add(TeamListElement(
                            teamID = team.teamID,
                            teamName = team.teamName,
                            teamStadium = team.teamStadium,
                            isNation = team.isNation,
                            teamNationality = team.teamNationality,
                            teamCity = team.teamCity
                        ))
                    }

                    searchResponseLiveData.postValue(listElements)
                }
            }

            override fun onFailure(call: Call<ApiResponseWrapper>, t: Throwable) {}
        })
    }
}