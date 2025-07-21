package io.miga.youtube.extractor

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AuthorExtractorTest {

    @Test
    fun `should extract author from youtube video page`() {
        // Given
        val html = """
            <!DOCTYPE html>
            <head>
                <div id="watch7-content" class="watch-main-col" itemscope itemid="https://www.youtube.com/watch?v=ne5eoNqCxVE" itemtype="http://schema.org/VideoObject">
                <span itemprop="author" itemscope itemtype="http://schema.org/Person">
                    <link itemprop="url" href="http://www.youtube.com/@bergerstefan">
                    <link itemprop="name" content="Stefan Berger">
                </span>
            </head>
        """.trimIndent()
        val document: Document = Jsoup.parse(html)

        // When
        val result = AuthorExtractor.extractAuthor(document)

        // Then
        assertNotNull(result)
        assertEquals("Stefan Berger", result.name)
        assertEquals("bergerstefan", result.handle)

    }

    @Test
    fun `should extract author from youtube video page and the element order doesn't matter`() {
        // Given
        val html = """
            <!DOCTYPE html>
            <head>
                <div id="watch7-content" class="watch-main-col" itemscope itemid="https://www.youtube.com/watch?v=ne5eoNqCxVE" itemtype="http://schema.org/VideoObject">
                <span itemprop="author" itemscope itemtype="http://schema.org/Person">
                    <link itemprop="name" content="Stefan Berger">
                    <link itemprop="url" href="http://www.youtube.com/@bergerstefan">
                </span>
            </head>
        """.trimIndent()
        val document: Document = Jsoup.parse(html)

        // When
        val result = AuthorExtractor.extractAuthor(document)

        // Then
        assertNotNull(result)
        assertEquals("Stefan Berger", result.name)
        assertEquals("bergerstefan", result.handle)

    }

    @Test
    fun `should default to empty string if we cannot find author data`() {
        // Given
        val html = """
            <!DOCTYPE html>
            <head>
            </head>
        """.trimIndent()
        val document: Document = Jsoup.parse(html)

        // When
        val result = AuthorExtractor.extractAuthor(document)

        // Then
        assertNotNull(result)
        assertEquals("", result.name)
        assertEquals("", result.handle)

    }

}