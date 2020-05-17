package com.maziyarbahramian.lastfm.ui

import com.maziyarbahramian.lastfm.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)

}