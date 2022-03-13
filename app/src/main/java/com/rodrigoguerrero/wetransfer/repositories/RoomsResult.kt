package com.rodrigoguerrero.wetransfer.repositories

sealed class RoomsResult {
    data class RoomBooked(val roomName: String) : RoomsResult()
    data class Error(val error: Throwable? = null) : RoomsResult()
    object BookingError : RoomsResult()
}
