package com.magnumrocha.kmm.playground.connection

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.outlined.SignalCellular4Bar
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.ui.graphics.vector.ImageVector
import com.magnumrocha.kmm.playground.android.R

val ConnectionType?.icon: ImageVector
    get() = when (this) {
        ConnectionType.WIFI -> Icons.Outlined.Wifi
        ConnectionType.MOBILE -> Icons.Outlined.SignalCellular4Bar
        null -> Icons.Filled.CloudOff
    }

val ConnectionType?.message: Int
    @StringRes get() = when (this) {
        ConnectionType.WIFI -> R.string.network_connection_wifi
        ConnectionType.MOBILE -> R.string.network_connection_mobile
        null -> R.string.network_connection_none
    }
