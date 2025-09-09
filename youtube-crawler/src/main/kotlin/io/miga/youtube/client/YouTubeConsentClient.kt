package io.miga.youtube.client

import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient

@Path("/")
@RegisterRestClient(configKey = "youtube-consent-client")
interface YouTubeConsentClient {

    @POST
    @Path("save?continue=https://www.youtube.com/&gl=DE&m=0&pc=yt&x=5&src=2&hl=de&bl=802715803&cm=2&set_eom=false&set_apyt=true&set_ytc=true")
    fun giveConsent()
}