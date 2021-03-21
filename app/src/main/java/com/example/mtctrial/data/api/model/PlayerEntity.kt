package com.example.mtctrial.data.api.model

import com.google.gson.annotations.SerializedName

class PlayerEntity {
    @SerializedName("playerID")
    var playerID: String? = null

    @SerializedName("playerFirstName")
    var playerFirstName: String? = null

    @SerializedName("playerSecondName")
    var playerSecondName: String? = null

    @SerializedName("playerNationality")
    var playerNationality: String? = null

    @SerializedName("playerAge")
    var playerAge: String? = null

    @SerializedName("playerClub")
    var playerClub: String? = null

    @SerializedName("isFavorite")
    var isFavorite: Boolean = false

    override fun toString(): String {
        return "$playerFirstName $playerSecondName"
    }
}