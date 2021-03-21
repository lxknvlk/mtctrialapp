package com.example.mtctrial.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "player")
data class PlayerData(
    @PrimaryKey @NotNull @ColumnInfo(name = "playerID") val playerID: String,
    @ColumnInfo(name = "playerFirstName") val playerFirstName: String?,
    @ColumnInfo(name = "playerSecondName") val playerSecondName: String?,
    @ColumnInfo(name = "playerNationality") val playerNationality: String?,
    @ColumnInfo(name = "playerAge") val playerAge: String?,
    @ColumnInfo(name = "playerClub") val playerClub: String?
)