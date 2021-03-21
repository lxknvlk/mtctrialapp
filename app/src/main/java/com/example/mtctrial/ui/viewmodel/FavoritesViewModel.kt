package com.example.mtctrial.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.mtctrial.TrialApplication
import com.example.mtctrial.data.database.mapper.PlayerMapper
import com.example.mtctrial.data.database.repository.DataRepository
import com.example.mtctrial.ui.adapter.PlayerListElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    init {
        (application as TrialApplication).appComponent.inject(this)
    }

    @Inject lateinit var dataRepository: DataRepository
    @Inject lateinit var playerMapper: PlayerMapper

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