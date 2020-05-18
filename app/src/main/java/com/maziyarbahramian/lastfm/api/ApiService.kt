package com.maziyarbahramian.lastfm.api

import androidx.lifecycle.LiveData
import com.maziyarbahramian.lastfm.api.networkResponse.SearchResponse
import com.maziyarbahramian.lastfm.api.networkResponse.TopAlbumsResponse
import com.maziyarbahramian.lastfm.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?method=artist.search&format=json")
    fun searchArtist(
        @Query("artist") artistName: String
    ): LiveData<GenericApiResponse<SearchResponse>>

    @GET("?method=artist.gettopalbums&format=json")
    fun getTopAlbumsOfArtist(
        @Query("artist") artistName: String
    ): LiveData<GenericApiResponse<TopAlbumsResponse>>

}