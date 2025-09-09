package io.miga.youtube.data

import java.time.Instant

data class VideoMetaData(
    val id: String,
    val title: String,
    val description: String,
    val author: VideoMetaDataAuthor,
    val duration: String,
    val uploadDate: Instant,
    val publishDate: Instant,
)