package io.miga.youtube.extractor

import io.miga.youtube.data.VideoMetaDataAuthor
import org.jsoup.nodes.Document

class AuthorExtractor {

    companion object {
        fun extractAuthor(document: Document): VideoMetaDataAuthor {
            val nameElements = document.select("span[itemprop=\"author\"]>link[itemprop=\"name\"]")
            val urlElements = document.select("span[itemprop=\"author\"]>link[itemprop=\"url\"]")

            val name = if (nameElements.isNotEmpty()) {
                nameElements[0].attr("content")
            } else {
                ""
            }

            val handle = if (urlElements.isNotEmpty()) {
                extractHandleFrom(urlElements[0].attr("href"))
            } else {
                ""
            }

            return VideoMetaDataAuthor(
                name = name,
                handle = handle
            )
        }

        private fun extractHandleFrom(text: String): String {
            return text.substringAfter("@")
        }
    }
}