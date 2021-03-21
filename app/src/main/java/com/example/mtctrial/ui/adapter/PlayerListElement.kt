package com.example.mtctrial.ui.adapter

class PlayerListElement(
    var playerID: String = "",
    var playerFirstName: String = "",
    var playerSecondName: String = "",
    var playerNationality: String = "",
    var playerAge: String = "",
    var playerClub: String = "",
    var isFavorite: Boolean = false
) : ListElement() {
    override fun toString(): String {
        return "$playerFirstName $playerSecondName $isFavorite"
    }
}