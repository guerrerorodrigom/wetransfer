package com.rodrigoguerrero.wetransfer.di

import com.rodrigoguerrero.wetransfer.repositories.RoomsRepository
import com.rodrigoguerrero.wetransfer.repositories.RoomsRepositoryImpl
import com.rodrigoguerrero.wetransfer.storage.RoomDataSource
import com.rodrigoguerrero.wetransfer.storage.RoomDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RoomsBindings {

    @Binds
    abstract fun bindRoomsDataSource(roomsDataSourceImpl: RoomDataSourceImpl): RoomDataSource
}
