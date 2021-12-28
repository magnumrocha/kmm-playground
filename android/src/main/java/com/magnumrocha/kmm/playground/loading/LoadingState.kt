package com.magnumrocha.kmm.playground.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.magnumrocha.kmm.playground.ui.theme.KMMPlaygroundTheme

@Composable
fun LoadingState(overlayColor: Color = Color.Transparent) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = overlayColor
    ) {
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
private fun LoadingStatePreview() {
    KMMPlaygroundTheme {
        LoadingState()
    }
}
