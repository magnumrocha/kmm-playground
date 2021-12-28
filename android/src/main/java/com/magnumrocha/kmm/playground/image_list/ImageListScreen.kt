package com.magnumrocha.kmm.playground.image_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.magnumrocha.kmm.playground.android.R
import com.magnumrocha.kmm.playground.picsum.PicsumImage
import com.magnumrocha.kmm.playground.picsum.getImageWithSize
import com.magnumrocha.kmm.playground.ui.theme.KMMPlaygroundTheme

@Composable
fun ImageListScreen(
    imageListViewModel: ImageListViewModel = viewModel()
) {
    val lazyImagesItems = imageListViewModel.observeImages().collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.images_title)) })
        }
    ) { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            items(lazyImagesItems) { image ->
                ImageItem(picsumImage = image!!)
            }

            lazyImagesItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingItem(modifier = Modifier.fillParentMaxSize()) }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem(modifier = Modifier.fillMaxWidth()) }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val error = lazyImagesItems.loadState.refresh as LoadState.Error
                        item {
                            FailureItem(
                                throwable = error.error,
                                onRetry = { retry() },
                                modifier = Modifier.fillParentMaxSize()
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val error = lazyImagesItems.loadState.refresh as LoadState.Error
                        item {
                            FailureItem(
                                throwable = error.error,
                                onRetry = { retry() },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImageItem(
    picsumImage: PicsumImage,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .requiredHeight(250.dp)
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            painter = rememberImagePainter(
                data = picsumImage.getImageWithSize(width = 400, height = 300),
                builder = {
                    crossfade(enable = true)
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )

        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                        startY = 300f
                    )
                )
        )

        Text(
            text = picsumImage.author,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(), maxLines = 1
        )
    }
}

@Composable
fun LoadingItem(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun FailureItem(
    throwable: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = throwable.message ?: stringResource(id = R.string.unknown_error),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.images_retry))
        }
    }
}

@Preview(name = "Image Item")
@Composable
private fun ImageItemPreview() {
    KMMPlaygroundTheme {
        ImageItem(
            PicsumImage(
                id = "0",
                author = "Alejandro Escamilla",
                width = 5616,
                height = 3744,
                url = "https://unsplash.com/photos/yC-Yzbqy7PY",
                downloadUrl = "https://picsum.photos/id/0/5616/3744"
            )
        )
    }
}

@Preview(name = "Loading Item")
@Composable
private fun LoadingItemPreview() {
    KMMPlaygroundTheme {
        LoadingItem(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(name = "Failure Item")
@Composable
private fun FailureItemPreview() {
    KMMPlaygroundTheme {
        FailureItem(
            throwable = Throwable("Crazy error!"),
            onRetry = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
