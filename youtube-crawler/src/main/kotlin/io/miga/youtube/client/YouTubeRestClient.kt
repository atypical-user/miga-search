package io.miga.youtube.client

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.QueryParam
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@Path("/")
@RegisterRestClient(configKey = "youtube-web-client")
interface YouTubeRestClient {

    @GET
    fun getStartPageContent(): String

    @GET
    @Path("/watch")
    fun getVideoPageContent(@QueryParam("v") v: String): String
}