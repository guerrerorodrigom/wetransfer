package com.rodrigoguerrero.wetransfer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoguerrero.wetransfer.models.Room
import com.rodrigoguerrero.wetransfer.models.RoomDTO
import com.rodrigoguerrero.wetransfer.repositories.RoomsRepository
import com.rodrigoguerrero.wetransfer.repositories.RoomsResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomsViewModel @Inject constructor(
    private val roomsRepository: RoomsRepository
): ViewModel() {

    val rooms: Flow<List<Room>> get() = roomsRepository.rooms

    private val _roomBooked = MutableStateFlow("")
    val bookedRoom: Flow<String> get() = _roomBooked

    fun fetchRooms() {
        viewModelScope.launch {
            roomsRepository.fetchRooms().collect { result -> handleResult(result) }
        }
    }

    fun bookRoom(room: Room) {
        viewModelScope.launch {
            roomsRepository.bookRoom(room)
                .collect { result -> handleResult(result) }
        }
    }

    fun clearBookedRoom() {
        _roomBooked.value = ""
    }

    private fun handleResult(roomsResult: RoomsResult) {
        when (roomsResult) {
            is RoomsResult.RoomBooked -> _roomBooked.value = roomsResult.roomName
        }
    }
}