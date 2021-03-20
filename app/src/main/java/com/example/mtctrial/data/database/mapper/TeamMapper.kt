package com.example.mtctrial.data.database.mapper

import com.example.mtctrial.data.api.model.TeamEntity
import com.example.mtctrial.data.database.model.TeamData

class TeamMapper {
    fun dataToEntity(data: TeamData): TeamEntity {
        val entity = TeamEntity()
        entity.teamName = data.teamName
        entity.teamCity = data.teamCity
        entity.teamID = data.teamID
        entity.teamNationality = data.teamNationality
        entity.teamStadium = data.teamStadium
        entity.isNation = data.isNation
        return entity
    }

    fun entityToData(entity: TeamEntity): TeamData {
        return TeamData(
            teamNationality = entity.teamNationality,
            teamCity = entity.teamCity,
            teamStadium = entity.teamStadium,
            teamName = entity.teamName,
            teamID = entity.teamID ?: "",
            isNation = entity.isNation
        )
    }

}