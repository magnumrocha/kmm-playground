package com.magnumrocha.kmm.playground.connection

import dev.tmapps.konnection.Konnection

actual class KonnectionFactory(private val enableDebugLog: Boolean) {
    actual fun createInstance(): Konnection = Konnection(enableDebugLog = enableDebugLog)
}

fun ConnectionState.stopConnectionObservation() {
    konnection.stop()
}
