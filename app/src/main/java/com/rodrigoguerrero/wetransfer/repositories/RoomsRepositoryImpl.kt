package com.rodrigoguerrero.wetransfer.repositories

import com.rodrigoguerrero.wetransfer.models.Room
import com.rodrigoguerrero.wetransfer.network.RoomsService
import com.rodrigoguerrero.wetransfer.storage.RoomDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RoomsRepositoryImpl @Inject constructor(
    private val roomsService: RoomsService,
    private val roomsDataSource: RoomDataSource,
    private val dispatcher: CoroutineDispatcher
) : RoomsRepository {

    override val rooms: Flow<List<Room>>
        get() = roomsDataSource.rooms

    override suspend fun fetchRooms(): Flow<RoomsResult> =
        flow {
            val response = roomsService.getRooms()

            if (response.isSuccessful) {
                roomsDataSource.addRooms(response.body()?.rooms?.map {
                    Room(
                        it.name,
                        it.spots,
                        it.thumbnail
                    )
                } ?: emptyList())
            } else {
                emit(RoomsResult.Error())
            }
        }
            .catch { emit(RoomsResult.Error(it)) }
            .flowOn(dispatcher)

    override suspend fun bookRoom(room: Room): Flow<RoomsResult> =
        flow {
            val response = roomsService.bookRoom()

            if (response.isSuccessful && response.body()?.success == true) {
                roomsDataSource.updateRoom(room.copy(spots = room.spots - 1))
                emit(RoomsResult.RoomBooked(room.name))
            } else {
                emit(RoomsResult.BookingError)
            }
        }
            .catch { emit(RoomsResult.BookingError) }
            .flowOn(dispatcher)

}
