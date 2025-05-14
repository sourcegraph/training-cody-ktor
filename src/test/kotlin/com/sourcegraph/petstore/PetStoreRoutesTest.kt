package com.sourcegraph.petstore

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.server.routing.*
import com.sourcegraph.petstore.routes.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PetStoreRoutesTest {

    @Test
    fun testRouteSetup() = testApplication {
        environment {
            // Use a completely fresh Environment setup
            developmentMode = false
            module {
                routing {
                    route("/api") {
                        categoryRoutes()
                        petRoutes()
                        tagRoutes()
                    }
                }
            }
        }
        
        // Test that the routes respond with 200 OK
        client.get("/api/categories/random").let { response ->
            assertEquals(HttpStatusCode.OK, response.status)
        }
        
        client.get("/api/pets/random").let { response ->
            assertEquals(HttpStatusCode.OK, response.status)
        }
        
        client.get("/api/tags/random").let { response ->
            assertEquals(HttpStatusCode.OK, response.status)
        }
    }
}