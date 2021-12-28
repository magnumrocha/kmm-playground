package com.magnumrocha.kmm.playground.picsum

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val BASE_IMAGE_URL = "https://picsum.photos/id"

@Serializable
data class PicsumImage(
    @SerialName("id") val id: String,
    @SerialName("author") val author: String,
    @SerialName("width") val width: Int,
    @SerialName("height") val height: Int,
    @SerialName("url") val url: String,
    @SerialName("download_url") val downloadUrl: String
)

fun PicsumImage.getImageWithSize(width: Int = 400, height: Int = 300) =
    "$BASE_IMAGE_URL/${this.id}/$width/$height"
