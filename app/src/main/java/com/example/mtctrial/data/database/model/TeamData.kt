package com.example.mtctrial.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "team")
data class TeamData(
    @PrimaryKey @NotNull @ColumnInfo(name = "teamID") val teamID: String,
    @ColumnInfo(name = "teamName") val teamName: String?,
    @ColumnInfo(name = "teamStadium") val teamStadium: String?,
    @ColumnInfo(name = "isNation") val isNation: String?,
    @ColumnInfo(name = "teamNationality") val teamNationality: String?,
    @ColumnInfo(name = "teamCity") val teamCity: String?
)