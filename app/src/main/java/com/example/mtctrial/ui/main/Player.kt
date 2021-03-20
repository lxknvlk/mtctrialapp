package com.example.mtctrial.ui.main

import com.google.gson.annotations.SerializedName

class Player {
    @SerializedName("playerID")
    var playerID: String? = null

    @SerializedName("playerFirstName")
    var playerFirstName: String? = null

    @SerializedName("playerSecondName")
    var playerSecondName: String? = null

    @SerializedName("playerNationality")
    var playerNationality: String? = null

    @SerializedName("playerAge")
    var playerAge: Int = 0

    @SerializedName("playerClub")
    var playerClub: String? = null
}