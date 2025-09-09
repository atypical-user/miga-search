package io.miga.youtube.extractor

import io.miga.youtube.data.VideoMetaData
import io.miga.youtube.exception.MetaDataNotFoundException
import jakarta.enterprise.context.ApplicationScoped
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.time.Instant

class VideoMetaDataExtractor {

    companion object {
        fun extractMetaData(html: String): VideoMetaData {
            val document: Document = Jsoup.parse(html)

            return VideoMetaData(
                id = extractItemProp(document, "identifier"),
                title = extractString(document, "title"),
                description = extractString(document, "description"),
                duration = extractDuration(document),
                author = AuthorExtractor.extractAuthor(document),
                uploadDate = extractDate(document, "uploadDate"),
                publishDate = extractDate(document, "datePublished"),
            )
        }



        private fun extractDuration(document: Document): String {
            return "0"
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

}