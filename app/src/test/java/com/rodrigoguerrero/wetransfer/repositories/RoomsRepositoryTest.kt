package com.rodrigoguerrero.wetransfer.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rodrigoguerrero.wetransfer.network.RoomsService
import com.rodrigoguerrero.wetransfer.storage.RoomDataSource
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
class RoomsRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val roomService = mockk<RoomsService>()
    private val roomDataSource = mockk<RoomDataSource>()

    private val subject = RoomsRepositoryImpl(roomService, roomDataSource, testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}