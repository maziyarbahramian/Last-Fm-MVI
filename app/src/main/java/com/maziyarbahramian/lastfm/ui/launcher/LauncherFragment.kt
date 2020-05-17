package com.maziyarbahramian.lastfm.ui.launcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.maziyarbahramian.lastfm.R
import com.maziyarbahramian.lastfm.ui.MainViewModel
import com.maziyarbahramian.lastfm.ui.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_launcher.*
import java.lang.Exception


class LauncherFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        subscribeObservers()
        searchButton.setOnClickListener {
            triggerSearchArtistEvent()
        }
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            //todo handle loading and messages
            println("DEBUG: DataState: ${dataState}")

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
                //todo setting artist to recyclerview
                println("DEBUG: Setting blog posts to RecyclerView: $it")
            }
        })

    }

    private fun triggerSearchArtistEvent() {
        viewModel.setStateEvent(MainStateEvent.SearchArtistEvent("cher"))
    }

    private fun navAlbumList() {
        findNavController().navigate(R.id.action_launcherFragment_to_albumsListFragment)
    }

    private fun navDetail() {
        findNavController().navigate(R.id.action_launcherFragment_to_detailFragment)
    }
}
