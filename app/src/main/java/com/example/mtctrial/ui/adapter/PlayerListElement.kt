package com.example.mtctrial.ui.adapter

class PlayerListElement(
    var playerID: String = "",
    var playerFirstName: String = "",
    var playerSecondName: String = "",
    var playerNationality: String = "",
    var playerAge: Int = 0,
    var playerClub: String = ""
) : ListElement()