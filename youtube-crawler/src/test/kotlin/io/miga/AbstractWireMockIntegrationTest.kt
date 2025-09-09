package io.miga

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.configureFor
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

abstract class AbstractWireMockIntegrationTest {

    lateinit var wireMockServer: WireMockServer

    @BeforeEach
    fun beforeEach() {
        wireMockServer = WireMockServer(9001) // No idea why we need the port twice
        configureFor(9001)
        wireMockServer.start()
    }

    @AfterEach
    fun afterEach() {
        wireMockServer.stop()
    }
}