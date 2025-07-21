package io.miga.youtube.extractor

import io.miga.youtube.exception.MetaDataNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import uk.org.webcompere.testgadgets.testdatafactory.TestData
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFactory

@TestDataFactory
class VideoMetaDataExtractorTest {

    private val extractor = VideoMetaDataExtractor()

    @TestData("youtube_video_page.html.txt")
    lateinit var videoPageHtml: String

    @Test
    fun `should extract video meta data from html string`() {
        // Given

        // When
        val videoMetaData = extractor.extractMetaData(videoPageHtml)

        // Then
        assertNotNull(videoMetaData)
        assertEquals("Fehler beim WANDERN (und wie du sie vermeiden kannst)", videoMetaData.videoTitle)
        assertEquals("ne5eoNqCxVE", videoMetaData.videoId)
        assertNotNull(videoMetaData.videoAuthor)
        assertEquals("Stefan Berger", videoMetaData.videoAuthor.name)
        assertEquals("bergerstefan", videoMetaData.videoAuthor.handle)
    }

    @Test
    fun `should throw exception when meta data field is null`() {
        // Given
        val html = """
            <html>
            <head>
            </head>
            <body>
            </body>
            </html>
        """.trimIndent()

        // When
        var exception: Exception? = null
        try {
            extractor.extractMetaData(html)
        } catch (e: Exception) {
            exception = e
        }

        // Then
        assertNotNull(exception)
        assertEquals(MetaDataNotFoundException::class.java, exception!!::class.java)
        assertEquals("MetaData (identifier) not found", exception.message)
    }

}