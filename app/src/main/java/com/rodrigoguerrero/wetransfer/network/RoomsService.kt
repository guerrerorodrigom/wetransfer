package com.rodrigoguerrero.wetransfer.network

import com.rodrigoguerrero.wetransfer.models.BookResponse
import com.rodrigoguerrero.wetransfer.models.RoomsResponse
import retrofit2.Response
import retrofit2.http.GET

interface RoomsService {

    @GET("rooms.json")
    suspend fun getRooms(): Response<RoomsResponse>

    @GET("bookRoom.json")
    suspend fun bookRoom(): Response<BookResponse>
}
