package com.example.mtctrial.data.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.example.mtctrial.data.api.ApiClient
import com.example.mtctrial.data.database.AppDatabase
import com.example.mtctrial.data.database.dao.PlayerDao
import com.example.mtctrial.data.database.dao.TeamDao
import com.example.mtctrial.data.database.mapper.PlayerMapper
import com.example.mtctrial.data.database.mapper.TeamMapper
import com.example.mtctrial.data.database.repository.DataRepository
import com.example.mtctrial.ui.viewmodel.MainViewModel
import com.example.mtctrial.ui.viewmodelfactory.MainViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DaggerModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideDatabase(): AppDatabase {
        return AppDatabase.getInstance(application)
    }

    @Provides
    @Singleton
    fun providePlayerDao(appDatabase: AppDatabase): PlayerDao {
        return appDatabase.playerDao()
    }

    @Provides
    @Singleton
    fun provdeTeamDao(appDatabase: AppDatabase): TeamDao {
        return appDatabase.teamDao()
    }

    @Provides
    @Singleton
    fun providesPlayerMapper(): PlayerMapper {
        return PlayerMapper()
    }

    @Provides
    @Singleton
    fun providesTeamMapper(): TeamMapper {
        return TeamMapper()
    }

    @Provides
    @Singleton
    fun providesApiClient(): ApiClient {
        return ApiClient()
    }

    @Provides
    @Singleton
    fun providesDataRepository(
        appDatabase: AppDatabase,
        playerMapper: PlayerMapper,
        teamMapper: TeamMapper
    ): DataRepository {
        return DataRepository(
            appDatabase,
            playerMapper,
            teamMapper
        )
    }


}