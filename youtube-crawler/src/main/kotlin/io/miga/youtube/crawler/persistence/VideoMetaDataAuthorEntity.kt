package io.miga.youtube.crawler.persistence

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity(name = "yt_video_author")
class VideoMetaDataAuthorEntity: PanacheEntityBase {
    @Id
    lateinit var handle: String
    lateinit var name: String
}