package io.miga.youtube.service

import io.miga.youtube.crawler.persistence.VideoMetaDataAuthorEntity
import io.miga.youtube.crawler.persistence.VideoMetaDataEntity
import io.miga.youtube.data.VideoMetaData
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class PostgresVideoDataService: VideoDataService {

    @Transactional
    override fun saveVideoMetaData(videoMetaData: VideoMetaData) {
        val videoMetaDataAuthorEntity = VideoMetaDataAuthorEntity()
        videoMetaDataAuthorEntity.name = videoMetaData.author.name
        videoMetaDataAuthorEntity.handle = videoMetaData.author.handle
        videoMetaDataAuthorEntity.persist()

        val videoMetaDataEntity = VideoMetaDataEntity()
        videoMetaDataEntity.id = videoMetaData.id
        videoMetaDataEntity.title = videoMetaData.title
        videoMetaDataEntity.description = videoMetaData.description
        videoMetaDataEntity.author = videoMetaDataAuthorEntity
        videoMetaDataEntity.duration = Integer.valueOf(videoMetaData.duration)
        videoMetaDataEntity.uploadDate = videoMetaData.uploadDate
        videoMetaDataEntity.publishDate = videoMetaData.publishDate

        videoMetaDataEntity.persist()
    }
}