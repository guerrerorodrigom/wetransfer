package com.rodrigoguerrero.wetransfer.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.rodrigoguerrero.wetransfer.models.Room
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun RoomsScreen(
    rooms: List<Room>,
    onSnackbarDismissed: () -> Unit,
    bookedRoomName: String,
    onBookRoom: (Room) -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState(snackbarHostState = snackbarHostState)

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        LazyColumn {
            items(rooms) { room ->
                RoomCard(
                    room = room,
                    onRoomCardClicked = {},
                    onBookRoom = { onBookRoom(it) }
                )
            }
        }

        LaunchedEffect(bookedRoomName) {
            if (bookedRoomName.isNotEmpty()) {
                scope.launch {
                    when (snackbarHostState.showSnackbar("Room $bookedRoomName reserved!")) {
                        SnackbarResult.Dismissed -> onSnackbarDismissed()
                    }
                }
            }
        }
    }
}