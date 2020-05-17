package com.maziyarbahramian.lastfm.ui.launcher

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.maziyarbahramian.lastfm.R
import com.maziyarbahramian.lastfm.ui.DataStateListener
import com.maziyarbahramian.lastfm.ui.MainViewModel
import com.maziyarbahramian.lastfm.ui.state.MainStateEvent
import kotlinx.android.synthetic.main.fragment_launcher.*
import java.lang.ClassCastException
import java.lang.Exception


class LauncherFragment : Fragment() {

    private val TAG = this::class.java.name

    lateinit var viewModel: MainViewModel

    private lateinit var searchView: SearchView

    lateinit var dataStateListener: DataStateListener

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

        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        setHasOptionsMenu(true)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->

            //todo handle loading and messages
            println("DEBUG: DataState: ${dataState}")
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
                //todo setting artist to recyclerview
                println("DEBUG: Setting blog posts to RecyclerView: $it")
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

    private fun navAlbumList() {
        findNavController().navigate(R.id.action_launcherFragment_to_albumsListFragment)
    }

    private fun navDetail() {
        findNavController().navigate(R.id.action_launcherFragment_to_detailFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {

            dataStateListener = context as DataStateListener

        } catch (e: ClassCastException) {
            println("DEBUG: $context must implement DataStateListener.")
        }
    }
}
