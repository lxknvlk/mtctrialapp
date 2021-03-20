package com.example.mtctrial.data.model

import com.google.gson.annotations.SerializedName

class Team {
    @SerializedName("teamID")
    var teamID: String? = null

    @SerializedName("teamName")
    var teamName: String? = null

    @SerializedName("teamStadium")
    var teamStadium: String? = null

    @SerializedName("isNation")
    var isNation: String? = null

    @SerializedName("teamNationality")
    var teamNationality: String? = null

    @SerializedName("teamCity")
    var teamCity: String? = null
}