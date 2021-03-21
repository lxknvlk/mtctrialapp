package com.example.mtctrial.ui.adapter

data class ButtonListElement(var type: ListButtonType): ListElement() {
    enum class ListButtonType {
        BUTTON_MORE_PLAYERS,
        BUTTON_MORE_TEAMS
    }
}

