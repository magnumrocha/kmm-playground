package com.magnumrocha.kmm.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.magnumrocha.kmm.playground.image_list.ImageListScreen
import com.magnumrocha.kmm.playground.ui.theme.KMMPlaygroundTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // this app draws behind the system bars, so we want to handle fitting system windows
        //WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            KMMPlaygroundTheme {
                ImageListScreen()
            }
        }
    }
}
