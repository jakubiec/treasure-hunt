package com.github.jakubiec.treasure_hunt.adapters

import com.github.jakubiec.treasure_hunt.adapters.RestAdapter.GetTreasureResponse
import com.github.jakubiec.treasure_hunt.application.TreasureStorage
import com.github.jakubiec.treasure_hunt.validInputTreasureMap
import io.kotlintest.matchers.boolean.shouldBeTrue
import io.kotlintest.shouldBe
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class RestAdapterSpec {

    @Inject
    lateinit var server: EmbeddedServer

    @Inject
    @field:Client("/treasure")
    lateinit var client: HttpClient

    @Inject
    lateinit var treasureStorage: TreasureStorage

    @Test
    fun shouldReturnTreasureNotFound() {
        runBlocking {
            val result = runCatching {
               client
                    .toBlocking()
                    .exchange<GetTreasureResponse>("/11")
            }

            result.isFailure.shouldBeTrue()
            result.onFailure {
                error -> error.localizedMessage shouldBe "Treasure Not Found"
            }
        }
    }

    @Test
    fun shouldReturnPathToTreasure() {
        runBlocking {
            treasureStorage.store(validInputTreasureMap)
            val response = client
                .toBlocking()
                .retrieve("/11", GetTreasureResponse::class.java)

            response.treasureFoundAt shouldBe listOf("1 1", "5 5", "1 5", "2 1", "4 4", "3 2", "1 3", "2 5", "4 3")
        }
    }

}