package io.miga.youtube.service

import io.miga.youtube.data.VideoMetaData

interface VideoDataService {
    fun saveVideoMetaData(videoMetaData: VideoMetaData)
}