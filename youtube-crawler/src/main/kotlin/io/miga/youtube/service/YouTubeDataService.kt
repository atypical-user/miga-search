package io.miga.youtube.service

import io.miga.youtube.extractor.VideoIdExtractor
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class YouTubeDataService(
    val youTubeService: YouTubeService
) {

    fun getVideoIdsFromStartPage(): List<String> {
        val startPageContent = youTubeService.getStartPageContent()

        return VideoIdExtractor.extractVideoIdsFrom(startPageContent)
    }
}