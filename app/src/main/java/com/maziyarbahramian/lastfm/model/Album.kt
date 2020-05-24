package com.maziyarbahramian.lastfm.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


@Entity(
    tableName = "albums",
    foreignKeys = [
        ForeignKey(
            entity = Artist::class,
            parentColumns = ["artistId"],
            childColumns = ["artistId"],
            onDelete = CASCADE
        )
    ]
)
data class Album(
    @PrimaryKey(autoGenerate = true)
    val albumId: Int? = null,

    val artistId: Int? = null,

    val playCount: Int? = null,

    val name: String? = null
)