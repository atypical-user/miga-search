package io.miga.youtube.service

import com.github.tomakehurst.wiremock.client.WireMock.*
import io.miga.AbstractWireMockIntegrationTest
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull

@QuarkusTest
class HttpYouTubeServiceIntegrationTest : AbstractWireMockIntegrationTest() {

    @Inject
    private lateinit var youTubeService: HttpYouTubeService

    @Test
    fun shouldReturnStartPageContent() {
        // Given
        stubFor(
            get(urlEqualTo("/")).willReturn(
                aResponse()
                    .withHeader("Content-Type", "text/html")
                    .withBodyFile("io/miga/youtube/service/HttpYouTubeServiceIntegrationTest/get_youtube_start_page.response.html")
                .withStatus(200)
            )
        )


        // When
        val result = youTubeService.getStartPageContent()

        // Then
        assertNotNull(result)
        val containsDocument = result.contains("StartPage")
        assertEquals(true, containsDocument)
    }

    @Test
    fun shouldReturnVideoPageContent() {
        // Given
        val videoId = "123"
        stubFor(
            get(urlPathMatching("/watch"))
                .withQueryParam("v", equalTo(videoId))
                .willReturn(
                aResponse()
                    .withHeader("Content-Type", "text/html")
                    .withBodyFile("io/miga/youtube/service/HttpYouTubeServiceIntegrationTest/get_youtube_video_page.response.html")
                    .withStatus(200)
            )
        )


        // When
        val result = youTubeService.getVideoPageContent(videoId)

        // Then
        assertNotNull(result)
        val containsDocument = result.contains("VideoPage")
        assertEquals(true, containsDocument)
    }
}