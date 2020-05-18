package com.maziyarbahramian.lastfm.ui.state

import com.maziyarbahramian.lastfm.api.networkResponse.AlbumItem
import com.maziyarbahramian.lastfm.api.networkResponse.ArtistItem

data class MainViewState(
    var artistItems : List<ArtistItem?>? = null,
    var albumItems : List<AlbumItem?>? = null
)