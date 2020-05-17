package com.maziyarbahramian.lastfm.repository

import androidx.lifecycle.LiveData
import com.maziyarbahramian.lastfm.api.MyRetrofitBuilder
import com.maziyarbahramian.lastfm.api.networkResponse.SearchResponse
import com.maziyarbahramian.lastfm.ui.state.MainViewState
import com.maziyarbahramian.lastfm.util.ApiSuccessResponse
import com.maziyarbahramian.lastfm.util.DataState
import com.maziyarbahramian.lastfm.util.GenericApiResponse

object Repository {

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
                return MyRetrofitBuilder.apiService.searchArtist(
                    "53b11ed135fadc65d64fa8e092584e5c",
                    artistName
                )
            }
        }.asLiveData()
    }
}