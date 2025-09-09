package io.miga.youtube.service

import io.miga.youtube.crawler.persistence.VideoMetaDataEntity
import io.miga.youtube.data.VideoMetaData
import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.instancio.Instancio
import org.instancio.Select.field
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@QuarkusTest
class PostgresVideoDataServiceTest {

    @Inject
    lateinit var service: PostgresVideoDataService

    @BeforeEach
    fun setUp() {

    }

    @Test
    fun `should save video data in database`() {
        // Given
        val videoMetaData = Instancio.of(VideoMetaData::class.java)
            .set(field("duration"), "10")
            .create()


        // When
        service.saveVideoMetaData(videoMetaData)

        // Then
        val all = VideoMetaDataEntity.listAll()
        assertEquals(1, all.size)
    }

}