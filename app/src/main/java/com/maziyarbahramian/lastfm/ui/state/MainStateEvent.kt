package com.maziyarbahramian.lastfm.ui.state

sealed class MainStateEvent {

    class SearchArtistEvent(
        val artistName: String
    ) : MainStateEvent()

    class GetTopAlbumsOfArtistEvent(
        val artistName: String
    ) : MainStateEvent()


    class GetAlbumInfoEvent(
        val artistName: String,
        var albumName: String
    ) : MainStateEvent()

    class None : MainStateEvent()

}