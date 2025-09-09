package io.miga.youtube.crawler.persistence

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntityBase
import io.quarkus.hibernate.orm.panache.kotlin.PanacheQuery
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.Instant


@Entity(name = "yt_video_meta_data")
class VideoMetaDataEntity: PanacheEntityBase {
    @Id
    lateinit var id: String

    lateinit var title: String

    lateinit var description: String

    @ManyToOne
    @JoinColumn(name = "author")
    lateinit var author: VideoMetaDataAuthorEntity

    var duration: Int = 0

    @Column(name = "upload_date")
    lateinit var uploadDate: Instant

    @Column(name = "publish_date")
    lateinit var publishDate: Instant

    companion object: PanacheCompanion<VideoMetaDataEntity> {
    }
}