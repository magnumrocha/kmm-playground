package com.magnumrocha.kmm.playground.connection

import android.content.Context
import dev.tmapps.konnection.Konnection

actual class KonnectionFactory(private val context: Context, private val enableDebugLog: Boolean) {
    actual fun createInstance(): Konnection = Konnection(context, enableDebugLog)
}
