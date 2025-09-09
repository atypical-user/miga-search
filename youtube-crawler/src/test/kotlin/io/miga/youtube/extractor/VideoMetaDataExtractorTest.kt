package io.miga.youtube.extractor

import io.miga.youtube.exception.MetaDataNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import uk.org.webcompere.testgadgets.testdatafactory.TestData
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFactory

@TestDataFactory
class VideoMetaDataExtractorTest {

    @TestData("youtube_video_page.html.txt")
    lateinit var videoPageHtml: String

    @Test
    fun `should extract video meta data from html string`() {
        // Given

        // When
        val videoMetaData = VideoMetaDataExtractor.extractMetaData(videoPageHtml)

        // Then
        assertNotNull(videoMetaData)
        assertEquals("Fehler beim WANDERN (und wie du sie vermeiden kannst)", videoMetaData.title)
        assertEquals("ne5eoNqCxVE", videoMetaData.id)
        assertNotNull(videoMetaData.author)
        assertEquals("Stefan Berger", videoMetaData.author.name)
        assertEquals("bergerstefan", videoMetaData.author.handle)
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
            VideoMetaDataExtractor.extractMetaData(html)
        } catch (e: Exception) {
            exception = e
        }

        // Then
        assertNotNull(exception)
        assertEquals(MetaDataNotFoundException::class.java, exception!!::class.java)
        assertEquals("MetaData (identifier) not found", exception.message)
    }

}