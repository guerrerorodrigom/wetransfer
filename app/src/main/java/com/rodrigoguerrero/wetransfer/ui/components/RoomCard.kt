package com.rodrigoguerrero.wetransfer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rodrigoguerrero.wetransfer.R
import com.rodrigoguerrero.wetransfer.models.Room
import com.rodrigoguerrero.wetransfer.models.RoomDTO

@ExperimentalMaterialApi
@Composable
fun RoomCard(
    room: Room,
    onRoomCardClicked: (Room) -> Unit,
    onBookRoom: (Room) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(11.dp),
        onClick = { onRoomCardClicked(room) }
    ) {
        Column {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(11.dp)),
                model = ImageRequest.Builder(LocalContext.current).data(room.thumbnail).build(),
                contentScale = ContentScale.Crop,
                contentDescription = room.name
            )
            Row(
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Column {
                    Text(
                        text = room.name,
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "${room.spots} spots remaining",
                        style = MaterialTheme.typography.subtitle1
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onBookRoom(room) },
                    modifier = Modifier
                        .width(90.dp)
                        .height(29.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9F0086)),
                    shape = RoundedCornerShape(4.dp),
                    contentPadding = PaddingValues(0.dp),
                    enabled = room.spots > 0
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = stringResource(R.string.book),
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        }
    }
}
