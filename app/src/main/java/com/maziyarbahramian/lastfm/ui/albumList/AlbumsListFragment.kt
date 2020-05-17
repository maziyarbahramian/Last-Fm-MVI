package com.maziyarbahramian.lastfm.ui.albumList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maziyarbahramian.lastfm.R
import kotlinx.android.synthetic.main.fragment_albums_list.*


class AlbumsListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_albums_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailButton.setOnClickListener { navDetail() }
    }

    private fun navDetail() {
        findNavController().navigate(R.id.action_albumsListFragment_to_detailFragment)
    }

}
