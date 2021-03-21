package com.example.mtctrial.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.mtctrial.data.database.AppDatabase
import com.example.mtctrial.data.database.mapper.PlayerMapper
import com.example.mtctrial.data.database.mapper.TeamMapper
import com.example.mtctrial.data.database.repository.DataRepository
import com.example.mtctrial.ui.adapter.PlayerListElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val playerMapper: PlayerMapper by lazy {
        PlayerMapper()
    }

    private val teamMapper: TeamMapper by lazy {
        TeamMapper()
    }

    private val appDatabase: AppDatabase by lazy {
        AppDatabase.getInstance(application)
    }

    private val dataRepository: DataRepository by lazy {
        DataRepository(
            appDatabase,
            playerMapper,
            teamMapper
        )
    }

    val favoritePlayersLiveData: LiveData<List<PlayerListElement>> by lazy {
        Transformations.map(dataRepository.favoritePlayerLiveData) { playerEntityList ->
            playerEntityList.map { playerMapper.entityToListElement(it) }
        }
    }

    fun togglePlayerFavorite(element: PlayerListElement) {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.updatePlayer(playerMapper.listElementToEntity(element.copy(isFavorite = !element.isFavorite)))
        }
    }
}