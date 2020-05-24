package com.maziyarbahramian.lastfm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.maziyarbahramian.lastfm.api.MyRetrofitBuilder
import com.maziyarbahramian.lastfm.api.networkResponse.AlbumInfoResponse
import com.maziyarbahramian.lastfm.api.networkResponse.Artist
import com.maziyarbahramian.lastfm.api.networkResponse.SearchResponse
import com.maziyarbahramian.lastfm.api.networkResponse.TopAlbumsResponse
import com.maziyarbahramian.lastfm.persistence.LastFmDao
import com.maziyarbahramian.lastfm.ui.state.MainViewState
import com.maziyarbahramian.lastfm.util.ApiSuccessResponse
import com.maziyarbahramian.lastfm.util.DataState
import com.maziyarbahramian.lastfm.util.GenericApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.maziyarbahramian.lastfm.model.Artist as DbArtist

class Repository(private val lastFmDao: LastFmDao) {

    private val TAG = this::class.java.name

    fun searchArtist(artistName: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<SearchResponse, MainViewState>() {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<SearchResponse>) {
                result.value = DataState.data(
                    data = MainViewState(
                        artistItems = response.body.results?.artistmatches?.artist
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<SearchResponse>> {
                return MyRetrofitBuilder.apiService.searchArtist(artistName)
            }
        }.asLiveData()
    }

    fun getTopAlbumsOfArtist(artistName: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<TopAlbumsResponse, MainViewState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<TopAlbumsResponse>) {
                result.value = DataState.data(
                    data = MainViewState(
                        albumItems = response.body.topalbums?.album
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<TopAlbumsResponse>> {
                return MyRetrofitBuilder.apiService.getTopAlbumsOfArtist(artistName)
            }

        }.asLiveData()
    }

    fun getAlbumInfo(artistName: String, albumName: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<AlbumInfoResponse, MainViewState>() {

            override fun handleApiSuccessResponse(response: ApiSuccessResponse<AlbumInfoResponse>) {
                result.value = DataState.data(
                    data = MainViewState(
                        albumInfo = response.body.album
                    )
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<AlbumInfoResponse>> {
                return MyRetrofitBuilder.apiService.getAlbumInfo(artistName, albumName)
            }

        }.asLiveData()
    }
}