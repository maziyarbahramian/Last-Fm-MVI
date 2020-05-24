package com.maziyarbahramian.lastfm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.maziyarbahramian.lastfm.util.*
import kotlinx.coroutines.*

abstract class NetworkBoundResource<ResponseObject, ViewStateType> {
    protected val result = MediatorLiveData<DataState<ViewStateType>>()

    init {
        result.value = DataState.loading(true)

        GlobalScope.launch(Dispatchers.IO) {

            delay(1000L)
            withContext(Dispatchers.Main) {
                val apiResponse = createCall()

                result.addSource(apiResponse) { response ->

                    result.removeSource(apiResponse)

                    handleNetworkCall(response)

                }
            }
        }
    }

    private fun handleNetworkCall(response: GenericApiResponse<ResponseObject>) {
        when (response) {
            is ApiSuccessResponse -> {
                handleApiSuccessResponse(response)
            }

            is ApiErrorResponse -> {
                println("DEBUG: NetworkBoundResource: ${response.errorMessage}")
                onErrorReturn(response.errorMessage)
            }

            is ApiEmptyResponse -> {
                println("DEBUG: NetworkBoundResource: HTTP 204, Returned NOTHING!")
                onErrorReturn("HTTP 204, Returned NOTHING!")
            }
        }
    }

    abstract fun handleApiSuccessResponse(response: ApiSuccessResponse<ResponseObject>)

    private fun onErrorReturn(errorMessage: String) {
        result.value = DataState.error(errorMessage)
    }

    abstract fun createCall(): LiveData<GenericApiResponse<ResponseObject>>

    fun asLiveData() = result as LiveData<DataState<ViewStateType>>
}