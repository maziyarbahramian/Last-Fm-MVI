package com.maziyarbahramian.lastfm.ui

import android.app.Application
import androidx.lifecycle.*
import com.maziyarbahramian.lastfm.api.networkResponse.Album
import com.maziyarbahramian.lastfm.api.networkResponse.AlbumItem
import com.maziyarbahramian.lastfm.api.networkResponse.ArtistItem
import com.maziyarbahramian.lastfm.persistence.LastFmDatabase
import com.maziyarbahramian.lastfm.repository.Repository
import com.maziyarbahramian.lastfm.ui.state.MainStateEvent
import com.maziyarbahramian.lastfm.ui.state.MainStateEvent.*
import com.maziyarbahramian.lastfm.ui.state.MainViewState
import com.maziyarbahramian.lastfm.util.AbsentLiveData
import com.maziyarbahramian.lastfm.util.DataState

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository

    init {
        val lastFmDao = LastFmDatabase.getDatabase(application).getDao()
        repository = Repository(lastFmDao)
    }

    private val _stateEvent: MutableLiveData<MainStateEvent> = MutableLiveData()
    private val _viewState: MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
        get() = _viewState

    val dataState: LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent) { stateEvent ->
            stateEvent?.let {
                handleStateEvent(stateEvent)
            }
        }

    private fun handleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>> {
        return when (stateEvent) {
            is SearchArtistEvent -> {
                repository.searchArtist(stateEvent.artistName)
            }
            is GetTopAlbumsOfArtistEvent -> {
                repository.getTopAlbumsOfArtist(stateEvent.artistName)
            }
            is GetAlbumInfoEvent -> {
                repository.getAlbumInfo(stateEvent.artistName, stateEvent.albumName)
            }
            is None -> {
                AbsentLiveData.create()
            }
        }
    }

    fun setArtistListData(artists: List<ArtistItem?>) {
        val update = getCurrentViewStateOrNew()
        update.artistItems = artists
        _viewState.value = update
    }

    fun setAlbumListData(albums: List<AlbumItem?>) {
        val update = getCurrentViewStateOrNew()
        update.albumItems = albums
        _viewState.value = update
    }

    fun setAlbumInfoData(albumInfo: Album) {
        val update = getCurrentViewStateOrNew()
        update.albumInfo = albumInfo
        _viewState.value = update
    }

    private fun getCurrentViewStateOrNew(): MainViewState {
        return viewState.value?.let {
            it
        } ?: MainViewState()
    }

    fun setStateEvent(event: MainStateEvent) {
        _stateEvent.value = event
    }

}