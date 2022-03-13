package com.rodrigoguerrero.wetransfer.storage

import com.rodrigoguerrero.wetransfer.models.Room
import com.rodrigoguerrero.wetransfer.storage.dao.RoomsDao
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class RoomDataSourceTest {

    private val dao = mockk<RoomsDao> {
        every { addRooms(any()) } just runs
        every { updateRoom(any()) } just runs
    }

    private val subject = RoomDataSourceImpl(dao)
    private val rooms = listOf(Room("room1", 10, "thumbnail1"), Room("room2", 2, "thumbnail2"))
    private val roomsEntity =
        listOf(RoomEntity("room1", 10, "thumbnail1"), RoomEntity("room2", 2, "thumbnail2"))

    @Test
    fun `When calling addRooms, dao addRooms is called with room entity`() = runBlockingTest {
        subject.addRooms(rooms)

        coVerify(exactly = 1) { dao.addRooms(roomsEntity) }
    }

    @Test
    fun `When calling updateRoom, dao updateRoom is called with room entity`() = runBlockingTest {
        val room = Room("room3", 5, "thumb3")
        val roomEntity = RoomEntity("room3", 5, "thumb3")

        subject.updateRoom(room)

        coVerify(exactly = 1) { dao.updateRoom(roomEntity) }
    }
}