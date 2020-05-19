package com.maziyarbahramian.lastfm.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

import com.maziyarbahramian.lastfm.R
import com.maziyarbahramian.lastfm.api.networkResponse.Album
import com.maziyarbahramian.lastfm.ui.BaseFragment
import com.maziyarbahramian.lastfm.ui.state.MainStateEvent
import com.maziyarbahramian.lastfm.util.SpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val TAG = this::class.java.name

    private var artistName: String? = null
    private var albumName: String? = null
    private lateinit var trackRecyclerAdapter: TrackRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            artistName = it.getString("artistName")
            albumName = it.getString("albumName")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        setHasOptionsMenu(true)
        initRecyclerView()
        subscribeObservers()

        albumName?.let { albumName ->
            (activity as AppCompatActivity).supportActionBar?.title = "Album: $albumName"
            artistName?.let { artistName ->
                triggerGetAlbumInfoEvent(artistName, albumName)
            }
        }

    }

    private fun initRecyclerView() {
        tracks_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpacingItemDecoration(30))
            trackRecyclerAdapter = TrackRecyclerAdapter()
            adapter = trackRecyclerAdapter
        }
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            dataStateListener.onDataStateChange(dataState)

            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.albumInfo?.let {
                        viewModel.setAlbumInfoData(it)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.albumInfo?.let {
                setDataToView(it)
            }
        })
    }

    private fun setDataToView(album: Album) {
        trackRecyclerAdapter.submitList(album.tracks?.track!!)
        Glide.with(view?.context!!)
            .load(album.image?.get(2)?.text)
            .into(album_img)
        album_name_tv.text = album.name
        album_artist_name_tv.text = album.artist

        album_listener_tv.text =
            getString(R.string.listeners).format(album.listeners?.toInt()?.div(1000.0))

        album_play_count_tv.text =
            getString(R.string.play_count).format(album.playcount?.toInt()?.div(1000.0))
    }

    private fun triggerGetAlbumInfoEvent(artistName: String, albumName: String) {
        viewModel.setStateEvent(MainStateEvent.GetAlbumInfoEvent(artistName, albumName))
    }

}
