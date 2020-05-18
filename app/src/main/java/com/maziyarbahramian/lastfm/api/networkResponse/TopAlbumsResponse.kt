package com.maziyarbahramian.lastfm.api.networkResponse

import com.google.gson.annotations.SerializedName

data class TopAlbumsResponse(

    @field:SerializedName("topalbums")
    val topalbums: Topalbums? = null
)

data class Artist(

    @field:SerializedName("mbid")
    val mbid: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)

data class Topalbums(

    @field:SerializedName("@attr")
    val attr: Attr? = null,

    @field:SerializedName("album")
    val album: List<AlbumItem>? = null
)

data class AlbumItem(

    @field:SerializedName("image")
    val image: List<ImageItem?>? = null,

    @field:SerializedName("mbid")
    val mbid: String? = null,

    @field:SerializedName("artist")
    val artist: Artist? = null,

    @field:SerializedName("playcount")
    val playcount: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)