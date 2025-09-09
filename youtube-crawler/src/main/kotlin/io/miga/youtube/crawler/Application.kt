package io.miga.youtube.crawler

import io.miga.youtube.crawler.persistence.VideoMetaDataEntity
import io.miga.youtube.extractor.VideoIdExtractor
import io.miga.youtube.extractor.VideoMetaDataExtractor
import io.miga.youtube.service.VideoDataService
import io.miga.youtube.service.YouTubeService
import io.quarkus.runtime.Quarkus
import io.quarkus.runtime.QuarkusApplication
import io.quarkus.runtime.annotations.QuarkusMain
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@QuarkusMain
class Application(
    val youTubeService: YouTubeService,
    val videoDataService: VideoDataService
): QuarkusApplication {

    private val log: Logger = LoggerFactory.getLogger(Application::class.java)

    override fun run(vararg args: String?): Int {
        log.info("YouTube Crawler started")
        val startPageContent = youTubeService.getStartPageContent()

        val videoIds = VideoIdExtractor.extractVideoIdsFrom(startPageContent)

        var savedCounter = 0
        videoIds.forEach {
            val videoPageContent = youTubeService.getVideoPageContent(videoId = it)
            val entity = VideoMetaDataEntity.find("id", it).firstResult()
            if (entity != null) {
                val videoMetaData = VideoMetaDataExtractor.extractMetaData(videoPageContent)
                videoDataService.saveVideoMetaData(videoMetaData)
                log.info("Video Metadata for Video[{}] with ID[{}] saved in database", videoMetaData.title, it)
            } else {
                log.info("Video with id [{}] already in database", it)
            }
            savedCounter++
            Thread.sleep(500) // Sleep so we do not run into request limits
        }
        log.info("Saved metadata of $savedCounter pages")
        log.info("YouTube Crawler finished")
        return 0
    }
}