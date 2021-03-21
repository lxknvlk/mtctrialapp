package com.example.mtctrial.data.database.mapper

import com.example.mtctrial.data.api.model.PlayerEntity
import com.example.mtctrial.data.database.model.PlayerData
import com.example.mtctrial.ui.adapter.PlayerListElement

class PlayerMapper {
    fun dataToEntity(data: PlayerData): PlayerEntity {
        val entity = PlayerEntity()
        entity.playerID = data.playerID
        entity.playerFirstName = data.playerFirstName
        entity.playerSecondName = data.playerSecondName
        entity.playerAge = data.playerAge
        entity.playerClub = data.playerClub
        entity.playerNationality = data.playerNationality
        entity.isFavorite = data.isFavorite
        return entity
    }

    fun entityToData(entity: PlayerEntity): PlayerData{
        return PlayerData(
            playerID = entity.playerID ?: "",
            playerFirstName = entity.playerFirstName,
            playerSecondName = entity.playerSecondName,
            playerAge = entity.playerAge,
            playerClub = entity.playerClub,
            playerNationality = entity.playerNationality,
            isFavorite = entity.isFavorite
        )
    }

    fun entityToListElement(playerEntity: PlayerEntity): PlayerListElement {
        return PlayerListElement(
            playerID = playerEntity.playerID ?: "",
            playerFirstName = playerEntity.playerFirstName ?: "",
            playerSecondName = playerEntity.playerSecondName ?: "",
            playerNationality = playerEntity.playerNationality ?: "",
            playerAge = playerEntity.playerAge ?: "",
            playerClub = playerEntity.playerClub ?: "",
            isFavorite = playerEntity.isFavorite
        )
    }

    fun listElementToEntity(playerListElement: PlayerListElement): PlayerEntity {
        val entity = PlayerEntity()
        entity.playerID = playerListElement.playerID
        entity.playerFirstName = playerListElement.playerFirstName
        entity.playerSecondName = playerListElement.playerSecondName
        entity.playerAge = playerListElement.playerAge
        entity.playerClub = playerListElement.playerClub
        entity.playerNationality = playerListElement.playerNationality
        entity.isFavorite = playerListElement.isFavorite
        return entity
    }

}