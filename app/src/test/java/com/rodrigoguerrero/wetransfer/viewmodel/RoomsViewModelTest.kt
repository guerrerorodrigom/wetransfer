package com.rodrigoguerrero.wetransfer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rodrigoguerrero.wetransfer.models.Room
import com.rodrigoguerrero.wetransfer.repositories.RoomsRepository
import com.rodrigoguerrero.wetransfer.repositories.RoomsResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.isEmpty
import strikt.assertions.isEqualTo

@ExperimentalCoroutinesApi
class RoomsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val roomsFlow: MutableStateFlow<RoomsResult> = MutableStateFlow(RoomsResult.BookingError)
    private val bookFlow: MutableStateFlow<RoomsResult> = MutableStateFlow(RoomsResult.BookingError)

    private val repository = mockk<RoomsRepository> {
        coEvery { fetchRooms() } returns roomsFlow
        coEvery { bookRoom(any()) } returns bookFlow
    }

    private val subject = RoomsViewModel(repository)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `When fetching rooms, repository fetch rooms is called`() = runBlockingTest {
        subject.fetchRooms()

        coVerify(exactly = 1) { repository.fetchRooms() }
    }

    @Test
    fun `When repository has rooms, view model has rooms updated`() = runBlockingTest {
        val room = Room("room", 5, "")
        every { repository.rooms } returns MutableStateFlow(listOf(room))

        val job = launch {
            subject.rooms.collect { result ->
                expect {
                    that(result.size).isEqualTo(1)
                    that(result[0]).isEqualTo(room)
                }
            }
        }

        job.cancel()
    }

    @Test
    fun `When updating a room, bookRoom is called and room booked has correct value`() = runBlockingTest {
        val room = Room("room", 5, "")

        subject.bookRoom(room)
        bookFlow.emit(RoomsResult.RoomBooked(room.name))

        val job = launch {
            subject.bookedRoom.collect { result ->
                expect {
                    that(result).isEqualTo(room.name)
                }
            }
            coVerify(exactly = 1) { repository.bookRoom(room) }
        }

        job.cancel()
    }

    @Test
    fun `When clear booked room is called, booked room is set to empty`() = runBlockingTest {
        val room = Room("room", 5, "")
        bookFlow.emit(RoomsResult.RoomBooked(room.name))

        subject.clearBookedRoom()

        val job = launch {
            subject.bookedRoom.collect { result ->
                expectThat(result).isEmpty()
            }
        }

        job.cancel()
    }
}