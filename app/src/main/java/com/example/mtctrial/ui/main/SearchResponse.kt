package com.example.mtctrial.ui.main

import com.google.gson.annotations.SerializedName

class SearchResponse {
    @SerializedName("players")
    var players: List<Player>? = null

    @SerializedName("teams")
    var teams: List<Team>? = null

    @SerializedName("status")
    var status: Boolean = true

    @SerializedName("message")
    var message: String? = null

    @SerializedName("request_order")
    var request_order: Int = 0

    @SerializedName("searchType")
    var searchType: String? = null

    @SerializedName("minVer")
    var minVer: String? = null

    @SerializedName("serverAlert")
    var serverAlert: String? = null
}