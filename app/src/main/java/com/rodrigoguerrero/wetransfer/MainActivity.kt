package com.rodrigoguerrero.wetransfer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.rodrigoguerrero.wetransfer.ui.components.RoomsScreen
import com.rodrigoguerrero.wetransfer.ui.theme.WeTransferTheme
import com.rodrigoguerrero.wetransfer.viewmodel.RoomsViewModel
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val roomsViewModel: RoomsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        roomsViewModel.fetchRooms()

        setContent {
            val rooms by roomsViewModel.rooms.collectAsState(emptyList())
            val bookedRoom by roomsViewModel.bookedRoom.collectAsState("")

            WeTransferTheme {
                RoomsScreen(
                    rooms = rooms,
                    onSnackbarDismissed = { roomsViewModel.clearBookedRoom() },
                    bookedRoomName = bookedRoom,
                    onBookRoom = { roomsViewModel.bookRoom(it) }
                )
//                LazyColumn {
//                    items(rooms) { room ->
//                        RoomCard(
//                            room = room,
//                            onRoomCardClicked = {},
//                            onBookRoom = roomsViewModel::bookRoom
//                        )
//                    }
//                }
            }
        }
    }
}
