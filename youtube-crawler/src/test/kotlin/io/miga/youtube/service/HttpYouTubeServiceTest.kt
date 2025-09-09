package io.miga.youtube.service

import io.miga.youtube.client.YouTubeRestClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class HttpYouTubeServiceTest {

    private val youTubeRestClientMock = mock<YouTubeRestClient>()

    private val youTubeService: HttpYouTubeService = HttpYouTubeService(youTubeRestClientMock)

    @Test
    fun shouldReturnStartPageContent() {
        // Given
        given(youTubeRestClientMock.getStartPageContent())
            .willReturn("bla")

        // When
        val result = youTubeService.getStartPageContent()

        // Then
        assertEquals("bla", result)
    }

    @Test
    fun shouldReturnVideoPageContent() {
        // Given

        // When

        // Then
    }

}