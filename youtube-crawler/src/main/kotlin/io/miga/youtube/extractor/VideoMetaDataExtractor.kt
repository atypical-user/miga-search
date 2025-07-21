package io.miga.youtube.extractor

import io.miga.youtube.data.VideoMetaData
import io.miga.youtube.exception.MetaDataNotFoundException
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.time.Instant

class VideoMetaDataExtractor {
    fun extractMetaData(html: String): VideoMetaData {
        val document: Document = Jsoup.parse(html)

        return VideoMetaData(
            videoId = extractItemProp(document, "identifier"),
            videoTitle = extractString(document, "title"),
            videoDescription = extractString(document, "description"),
            videoDuration = "",
            videoAuthor = AuthorExtractor.extractAuthor(document),
            uploadDate = extractDate(document, "uploadDate"),
            publishDate = extractDate(document, "datePublished"),
        )
    }

    private fun extractString(document: Document, type: String) =
        document.select("meta[name=$type]").first()?.attributes()?.get("content")
            ?: throw MetaDataNotFoundException(type)

    private fun extractDate(document: Document, type: String): Instant {
        val uploadDate = document.select("meta[itemprop=$type]")
            .first()
            ?.attributes()
            ?.get("content")
            ?: return Instant.now()
        return Instant.parse(uploadDate)
    }

    private fun extractItemProp(document: Document, prop: String) =
        document.select("meta[itemprop=$prop]")
            .first()
            ?.attributes()
            ?.get("content")
            ?: throw MetaDataNotFoundException(prop)

}