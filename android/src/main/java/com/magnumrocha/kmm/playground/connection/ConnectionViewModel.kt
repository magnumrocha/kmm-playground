package com.magnumrocha.kmm.playground.connection

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ConnectionViewModel @Inject constructor(
    private val connectionState: ConnectionState
) : ViewModel() {

    fun observeConnection(): Flow<ConnectionType?> =
        connectionState.observeNetworkConnection()
}