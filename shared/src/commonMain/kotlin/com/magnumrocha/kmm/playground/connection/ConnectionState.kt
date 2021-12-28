package com.magnumrocha.kmm.playground.connection

import dev.tmapps.konnection.Konnection
import dev.tmapps.konnection.NetworkConnection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

expect class KonnectionFactory {
    fun createInstance(): Konnection
}

enum class ConnectionType {
    WIFI, MOBILE
}

class ConnectionState(konnectionFactory: KonnectionFactory) {
    internal val konnection = konnectionFactory.createInstance()

    fun observeNetworkConnection(): Flow<ConnectionType?> =
        konnection.observeNetworkConnection().map { it?.type }
}

private val NetworkConnection.type: ConnectionType
    get() = when (this) {
        NetworkConnection.WIFI -> ConnectionType.WIFI
        NetworkConnection.MOBILE -> ConnectionType.MOBILE
    }
