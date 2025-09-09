package io.miga.youtube.service


interface YouTubeService {

    fun giveConsent()

    fun getStartPageContent(): String

    fun getVideoPageContent(videoId: String): String
}