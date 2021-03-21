package com.example.mtctrial.ui.adapter

class TeamListElement(
    var teamID: String = "",
    var teamName: String = "",
    var teamStadium: String = "",
    var isNation: String = "",
    var teamNationality: String = "",
    var teamCity: String = ""
) : ListElement() {
    override fun toString(): String {
        return "$teamName"
    }
}