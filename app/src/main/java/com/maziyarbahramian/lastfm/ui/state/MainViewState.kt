package com.maziyarbahramian.lastfm.ui.state

import com.maziyarbahramian.lastfm.api.networkResponse.ArtistItem

data class MainViewState(
    var artistItems : List<ArtistItem?>? = null
)