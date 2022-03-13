package com.rodrigoguerrero.wetransfer.storage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey
    val name: String,
    val spots: Int,
    val thumbnail: String
)
