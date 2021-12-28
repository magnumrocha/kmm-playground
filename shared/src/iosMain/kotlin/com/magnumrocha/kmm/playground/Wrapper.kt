package com.magnumrocha.kmm.playground

import com.magnumrocha.kmm.playground.connection.ConnectionState
import com.magnumrocha.kmm.playground.connection.ConnectionType
import com.magnumrocha.kmm.playground.connection.KonnectionFactory
import com.magnumrocha.kmm.playground.connection.stopConnectionObservation
import com.magnumrocha.kmm.playground.picsum.PicsumImage
import com.magnumrocha.kmm.playground.picsum.PicsumImagesApi
import com.magnumrocha.kmm.playground.utils.HttpClientFactory
import com.magnumrocha.kmm.playground.utils.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.native.concurrent.freeze

class SharedWrapper {
    private val mainScope = MainScope()

    private val connectionState = ConnectionState(KonnectionFactory(enableDebugLog = true))

    private val httpClientFactory = HttpClientFactory(enableNetworkLogs = true)
    private val picsumImagesApi = PicsumImagesApi(httpClientFactory)

    fun stopNetworkObservation() {
        connectionState.stopConnectionObservation()
    }

    fun networkConnectionObservation(callback: (ConnectionType?) -> Unit) {
        connectionState.observeNetworkConnection()
            .onEach { callback(it) }
            .launchIn(mainScope)
    }

    fun getImages(page: Int, size: Int, callback: (List<PicsumImage>?, Throwable?) -> Unit) {
        mainScope.launch {
            try {
                val result = picsumImagesApi.get(page, size)
                callback.invoke(result.freeze(), null)
            } catch (error: Throwable) {
                callback.invoke(null, error)
            }
        }
    }
}
