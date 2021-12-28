package com.magnumrocha.kmm.playground.connection

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.magnumrocha.kmm.playground.ui.theme.KMMPlaygroundTheme

@Composable
fun ConnectionScreen(
    connectionViewModel: ConnectionViewModel = viewModel()
) {
    val connectionType = connectionViewModel.observeConnection().collectAsState(initial = null)
    ConnectionUI(connectionType.value)
}

@Composable
fun ConnectionUI(type: ConnectionType?) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                colorFilter = ColorFilter.tint(Color.White),
                imageVector = type.icon,
                modifier = Modifier.size(120.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = type.message),
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun ConnectionUIPreview() {
    KMMPlaygroundTheme {
        ConnectionUI(type = ConnectionType.WIFI)
    }
}
