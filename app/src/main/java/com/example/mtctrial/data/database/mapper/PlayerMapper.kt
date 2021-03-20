package com.example.mtctrial.data.database.mapper

import com.example.mtctrial.data.api.model.PlayerEntity
import com.example.mtctrial.data.database.model.PlayerData

class PlayerMapper {
    fun dataToEntity(data: PlayerData): PlayerEntity {
        val entity = PlayerEntity()
        entity.playerID = data.playerID
        entity.playerFirstName = data.playerFirstName
        entity.playerSecondName = data.playerSecondName
        entity.playerAge = data.playerAge
        entity.playerClub = data.playerClub
        entity.playerNationality = data.playerNationality
        return entity
    }

    fun entityToData(entity: PlayerEntity): PlayerData{
        return PlayerData(
            playerID = entity.playerID ?: "",
            playerFirstName = entity.playerFirstName,
            playerSecondName = entity.playerSecondName,
            playerAge = entity.playerAge,
            playerClub = entity.playerClub,
            playerNationality = entity.playerNationality
        )
    }

}