package io.miga.youtube.extractor

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import uk.org.webcompere.testgadgets.testdatafactory.TestData
import uk.org.webcompere.testgadgets.testdatafactory.TestDataFactory

@TestDataFactory
class VideoIdExtractorTest {

    private val extractor = VideoIdExtractor()

    @TestData("youtube_start_page.html")
    lateinit var testData: String

    @Test
    fun `should extract video ids from html string`() {
        // Given
        val testId = "ne5eoNqCxVE"
        val testId1 = "kCE2W_Q4_xQ"
        val testId2 = "GW-BY6nRjDc"
        val testId3 = "GW-BY6nRjDc"
        val testId4 = "GW-BY6nRjDD"
        val html = """
            <!DOCTYPE html>
            <head>
                <script nonce="IYBxmzPGBiQuT44IXiZ6Zg">
                    var ytInitialData = { 
                        "videoId": "$testId",
                        "anotherObject": {
                            "videoId": "$testId1",
                        },
                        "anArray": [
                            {
                                "videoId": "GW-BY6nRjDc"
                            }
                        ],
                        "videoIds": ["$testId3", "$testId4"]
                    };
                </script>
            </head>
        """.trimIndent()

        // When
        val result = extractor.extractVideoIdsFrom(html)

        // Then
        assertEquals(4, result.size)
        assertEquals(testId, result[0])
        assertEquals(testId1, result[1])
        assertEquals(testId2, result[2])
        assertEquals(testId4, result[3])

    }

    @Test
    fun `should extract videosId from html file`() {
        // When
        val result = extractor.extractVideoIdsFrom(testData)

        // Then
        assertEquals(41, result.size)
    }
}