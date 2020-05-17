package com.maziyarbahramian.lastfm.ui.launcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maziyarbahramian.lastfm.R
import kotlinx.android.synthetic.main.fragment_launcher.*


class LauncherFragment : Fragment() {

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
        albumListButton.setOnClickListener { navAlbumList() }
        detailButton.setOnClickListener { navDetail() }
    }

    private fun navAlbumList() {
        findNavController().navigate(R.id.action_launcherFragment_to_albumsListFragment)
    }

    private fun navDetail() {
        findNavController().navigate(R.id.action_launcherFragment_to_detailFragment)
    }
}
