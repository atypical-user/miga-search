package io.miga.youtube.service

import io.miga.youtube.client.YouTubeConsentClient
import io.miga.youtube.client.YouTubeRestClient
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.rest.client.inject.RestClient

@ApplicationScoped
class HttpYouTubeService(
    @RestClient
    private val youTubeRestClient: YouTubeRestClient,
    @RestClient
    private val youTubeConsentClient: YouTubeConsentClient,
): YouTubeService {

    override fun giveConsent() {
        youTubeConsentClient.giveConsent()
    }

    override fun getStartPageContent(): String {
        return youTubeRestClient.getStartPageContent()
    }

    override fun getVideoPageContent(videoId: String): String {
        return youTubeRestClient.getVideoPageContent(videoId)
    }
}