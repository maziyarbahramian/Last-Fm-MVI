package com.maziyarbahramian.lastfm.model

import androidx.room.Embedded
import androidx.room.Relation

class ArtistAndAlbums {
    @Embedded
    var artist: Artist? = null

    @Relation(
        parentColumn = "artistId",
        entityColumn = "artistId"
    )
    var albums: List<Album> = ArrayList()
}