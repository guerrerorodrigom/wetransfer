package com.rodrigoguerrero.wetransfer.di

import android.content.Context
import androidx.room.Room
import com.rodrigoguerrero.wetransfer.network.RoomsService
import com.rodrigoguerrero.wetransfer.repositories.RoomsRepository
import com.rodrigoguerrero.wetransfer.repositories.RoomsRepositoryImpl
import com.rodrigoguerrero.wetransfer.storage.RoomDataSource
import com.rodrigoguerrero.wetransfer.storage.RoomsDatabase
import com.rodrigoguerrero.wetransfer.storage.dao.RoomsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object RoomsModule {

    @Provides
    fun provideRoomsService(): RoomsService {
        return Retrofit.Builder()
            .baseUrl("https://wetransfer.github.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RoomsService::class.java)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RoomsDatabase =
        Room
            .databaseBuilder(
                context,
                RoomsDatabase::class.java,
                "rooms-database"
            )
            .build()

    @Provides
    fun provideRoomsDao(database: RoomsDatabase): RoomsDao = database.roomsDao()

    @Provides
    fun provideRepository(roomDataSource: RoomDataSource, roomsService: RoomsService): RoomsRepository {
        return RoomsRepositoryImpl(roomsService, roomDataSource, Dispatchers.IO)
    }
}
