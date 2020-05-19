package com.maziyarbahramian.lastfm.api.networkResponse

import com.google.gson.annotations.SerializedName

data class AlbumInfoResponse(

	@field:SerializedName("album")
	val album: Album? = null
)

data class Tracks(

	@field:SerializedName("track")
	val track: List<TrackItem?>? = null
)

data class Tags(

	@field:SerializedName("tag")
	val tag: List<TagItem?>? = null
)

data class TagItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class TrackItem(

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("@attr")
	val attr: Attr? = null,

	@field:SerializedName("streamable")
	val streamable: Streamable? = null,

	@field:SerializedName("artist")
	val artist: Artist? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class Album(

	@field:SerializedName("image")
	val image: List<ImageItem?>? = null,

	@field:SerializedName("mbid")
	val mbid: String? = null,

	@field:SerializedName("listeners")
	val listeners: String? = null,

	@field:SerializedName("artist")
	val artist: String? = null,

	@field:SerializedName("playcount")
	val playcount: String? = null,

	@field:SerializedName("wiki")
	val wiki: Wiki? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("tracks")
	val tracks: Tracks? = null,

	@field:SerializedName("tags")
	val tags: Tags? = null
)

data class Streamable(

	@field:SerializedName("#text")
	val text: String? = null,

	@field:SerializedName("fulltrack")
	val fulltrack: String? = null
)

data class Wiki(

	@field:SerializedName("summary")
	val summary: String? = null,

	@field:SerializedName("published")
	val published: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)
