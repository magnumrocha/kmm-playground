package com.magnumrocha.kmm.playground.picsum

import com.magnumrocha.kmm.playground.utils.HttpClientFactory
import io.ktor.client.request.get

class PicsumImagesApi(private val httpClientFactory: HttpClientFactory) {

    suspend fun get(page: Int, size: Int): List<PicsumImage> {
        val endpoint = buildEndpoint(page, size)
        return httpClientFactory.getInstance().get(endpoint)
    }

    private fun buildEndpoint(page: Int, size: Int): String =
        "https://picsum.photos/v2/list?page=$page&limit=$size"
}
