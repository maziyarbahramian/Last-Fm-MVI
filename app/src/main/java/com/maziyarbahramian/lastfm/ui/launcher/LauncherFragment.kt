package com.maziyarbahramian.lastfm.ui.launcher

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.maziyarbahramian.lastfm.R
import com.maziyarbahramian.lastfm.api.networkResponse.ArtistItem
import com.maziyarbahramian.lastfm.ui.BaseFragment
import com.maziyarbahramian.lastfm.ui.DataStateListener
import com.maziyarbahramian.lastfm.ui.MainViewModel
import com.maziyarbahramian.lastfm.ui.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_launcher.*
import java.lang.ClassCastException
import java.lang.Exception


class LauncherFragment : BaseFragment(R.layout.fragment_launcher) {

    private val TAG = this::class.java.name

    private lateinit var searchView: SearchView

    private lateinit var launcherRecyclerAdapter: LauncherRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)
        initRecyclerView()
        subscribeObservers()
    }

    private fun initRecyclerView() {
        launcher_recyclerview.apply {
            layoutManager = GridLayoutManager(activity, 2)
            launcherRecyclerAdapter = LauncherRecyclerAdapter { item -> onArtistItemSelected(item) }
            adapter = launcherRecyclerAdapter
        }
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            dataStateListener.onDataStateChange(dataState)

            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.artistItems?.let {
                        viewModel.setArtistListData(it)
                    }
                }
            }

        })

        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.artistItems?.let {
                println("DEBUG: Setting artists to RecyclerView: $it")
                launcherRecyclerAdapter.submitList(it)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
        initSearchView(menu)
    }

    private fun initSearchView(menu: Menu) {
        activity?.apply {
            val searchManager: SearchManager =
                getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView = menu.findItem(R.id.action_search).actionView as SearchView
            searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
            searchView.maxWidth = Integer.MAX_VALUE
            searchView.setIconifiedByDefault(true)
            searchView.isSubmitButtonEnabled = true
        }

        val searchPlate = searchView.findViewById(R.id.search_src_text) as EditText
        val searchButton = searchView.findViewById(R.id.search_go_btn) as View

        searchButton.setOnClickListener {
            val searchQuery = searchPlate.text.toString()
            Log.e(TAG, "SearchView: executing search...: ${searchQuery}")
            triggerSearchArtistEvent(searchQuery)
        }
    }

    private fun triggerSearchArtistEvent(searchQuery: String) {
        viewModel.setStateEvent(MainStateEvent.SearchArtistEvent(searchQuery))
    }

    private fun onArtistItemSelected(artistName: String) {
        Log.d(TAG, "onArtistItemSelected: $artistName")
        navAlbumList(artistName)
    }

    private fun navAlbumList(artistName: String) {
        val bundle = bundleOf("artistName" to artistName)
        findNavController().navigate(R.id.action_launcherFragment_to_albumsListFragment, bundle)
    }

    private fun navDetail() {
        findNavController().navigate(R.id.action_launcherFragment_to_detailFragment)
    }
}
