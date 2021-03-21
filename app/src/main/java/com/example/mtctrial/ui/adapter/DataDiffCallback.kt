package com.example.mtctrial.ui.adapter

import androidx.recyclerview.widget.DiffUtil

class DataDiffCallback(
    private val oldList: List<ListElement>,
    private val newList: List<ListElement>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is PlayerListElement && newItem is PlayerListElement){
            oldItem.playerID == newItem.playerID
        } else if (oldItem is TeamListElement && newItem is TeamListElement) {
            oldItem.teamID == newItem.teamID
        } else if(oldItem is SeparatorListElement && newItem is SeparatorListElement){
            oldItem.title == newItem.title
        } else {
            false
        }
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val oldItem = oldList[oldPosition]
        val newItem = newList[newPosition]

        return if (oldItem is PlayerListElement && newItem is PlayerListElement){
            oldItem.playerAge == newItem.playerAge
                    && oldItem.playerClub == newItem.playerClub
                    && oldItem.playerFirstName == newItem.playerFirstName
                    && oldItem.playerNationality == newItem.playerNationality
                    && oldItem.playerSecondName == newItem.playerSecondName
                    && oldItem.isFavorite == newItem.isFavorite
        } else if (oldItem is TeamListElement && newItem is TeamListElement) {
            oldItem.teamCity == newItem.teamCity
                    && oldItem.teamName == newItem.teamName
                    && oldItem.teamNationality == newItem.teamNationality
                    && oldItem.teamStadium == newItem.teamStadium
        } else if(oldItem is SeparatorListElement && newItem is SeparatorListElement){
            oldItem.title == newItem.title
        } else {
            false
        }
    }
}