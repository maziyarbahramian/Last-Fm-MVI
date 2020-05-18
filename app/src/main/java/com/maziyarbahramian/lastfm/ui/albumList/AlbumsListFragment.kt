package com.maziyarbahramian.lastfm.ui.albumList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.maziyarbahramian.lastfm.R
import com.maziyarbahramian.lastfm.ui.BaseFragment
import com.maziyarbahramian.lastfm.ui.DataStateListener
import com.maziyarbahramian.lastfm.ui.MainViewModel
import com.maziyarbahramian.lastfm.ui.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_albums_list.*
import java.lang.Exception


class AlbumsListFragment : BaseFragment(R.layout.fragment_albums_list) {

    private val TAG = this::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        subscribeObservers()
        val artistName = arguments?.getString("artistName")
        artistName?.let {
            Log.d(TAG, "argument from launcherFragment $it")
            triggerGetTopAlbumsOfArtistEvent(artistName)
        }

        detailButton.setOnClickListener { navDetail() }
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
                // todo setting albums to recyclerView
            }
        })
    }

    private fun triggerGetTopAlbumsOfArtistEvent(artistName: String) {
        viewModel.setStateEvent(MainStateEvent.GetTopAlbumsOfArtistEvent(artistName))
    }

    private fun navDetail() {
        findNavController().navigate(R.id.action_albumsListFragment_to_detailFragment)
    }

}
