package com.example.mtctrial.ui.adapter

class PlayerListElement(
    var playerID: String? = null,
    var playerFirstName: String? = null,
    var playerSecondName: String? = null,
    var playerNationality: String? = null,
    var playerAge: Int = 0,
    var playerClub: String? = null
) : ListElement()