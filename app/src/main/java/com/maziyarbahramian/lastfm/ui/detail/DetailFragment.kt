package com.maziyarbahramian.lastfm.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.maziyarbahramian.lastfm.R
import com.maziyarbahramian.lastfm.ui.BaseFragment
import com.maziyarbahramian.lastfm.ui.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment(R.layout.fragment_detail) {

    private val TAG = this::class.java.name

    private var artistName: String? = null
    private var albumName: String? = null

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
                Log.d(TAG, "Setting album info to View: $it")
            }
        })
    }

    private fun triggerGetAlbumInfoEvent(artistName: String, albumName: String) {
        viewModel.setStateEvent(MainStateEvent.GetAlbumInfoEvent(artistName, albumName))
    }

}
