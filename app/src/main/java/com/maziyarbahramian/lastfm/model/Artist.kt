package com.maziyarbahramian.lastfm.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "artists", indices = [Index("artistId")])
data class Artist(

    @PrimaryKey(autoGenerate = true)
    val artistId: Int?,

    val name: String?,

    val listeners: Int?
)