package com.maziyarbahramian.lastfm.ui.albumList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.maziyarbahramian.lastfm.R
import com.maziyarbahramian.lastfm.ui.BaseFragment
import com.maziyarbahramian.lastfm.ui.DataStateListener
import com.maziyarbahramian.lastfm.ui.MainViewModel
import com.maziyarbahramian.lastfm.ui.state.MainStateEvent
import com.maziyarbahramian.lastfm.util.SpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_albums_list.*
import java.lang.Exception


class AlbumsListFragment : BaseFragment(R.layout.fragment_albums_list) {

    private val TAG = this::class.java.name

    private lateinit var albumsRecyclerAdapter: AlbumsRecyclerAdapter
    private var artistName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            artistName = it.getString("artistName")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
        setHasOptionsMenu(true)
        initRecyclerView()
        subscribeObservers()

        artistName?.let {
            (activity as AppCompatActivity).supportActionBar?.title = "Artist: $artistName"
            triggerGetTopAlbumsOfArtistEvent(it)
            Log.d(TAG, "argument from launcherFragment $it")
        }
    }

    private fun initRecyclerView() {
        album_list_recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(SpacingItemDecoration(30))
            albumsRecyclerAdapter =
                AlbumsRecyclerAdapter() { albumName, artistName ->
                    onAlbumItemSelected(
                        albumName,
                        artistName
                    )
                }
            adapter = albumsRecyclerAdapter
        }
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            dataStateListener.onDataStateChange(dataState)

            dataState.data?.let { event ->

                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.albumItems?.let {
                        viewModel.setAlbumListData(it)
                    }
                }
            }
        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.albumItems?.let {
                Log.d(TAG, "Setting albums to RecyclerView: $it")
                albumsRecyclerAdapter.submitList(it)
            }
        })
    }

    private fun triggerGetTopAlbumsOfArtistEvent(artistName: String) {
        viewModel.setStateEvent(MainStateEvent.GetTopAlbumsOfArtistEvent(artistName))
    }

    private fun onAlbumItemSelected(albumName: String, artistName: String) {
        Log.d(TAG, "onArtistItemSelected: $albumName, $artistName")
        navDetail(albumName, artistName)
    }

    private fun navDetail(albumName: String, artistName: String) {
        val bundle = bundleOf("albumName" to albumName, "artistName" to artistName)
        findNavController().navigate(R.id.action_albumsListFragment_to_detailFragment, bundle)
    }

}
