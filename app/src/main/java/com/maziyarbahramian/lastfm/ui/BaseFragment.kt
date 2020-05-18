package com.maziyarbahramian.lastfm.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import java.lang.ClassCastException
import java.lang.Exception

abstract class BaseFragment(
    @LayoutRes
    private val layoutRes: Int
) : Fragment(layoutRes) {

    lateinit var viewModel: MainViewModel

    lateinit var dataStateListener: DataStateListener

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

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