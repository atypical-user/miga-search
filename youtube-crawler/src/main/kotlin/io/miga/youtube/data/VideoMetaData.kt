package io.miga.youtube.data

import java.time.Instant

data class VideoMetaData(
    val videoId: String,
    val videoTitle: String,
    val videoDescription: String,
    val videoAuthor: VideoMetaDataAuthor,
    val videoDuration: String,
    val uploadDate: Instant,
    val publishDate: Instant,
)