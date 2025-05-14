package com.sourcegraph.petstore

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.server.routing.*
import com.sourcegraph.petstore.routes.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testApiResponses() = testApplication {
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
        
        // Test all API endpoints return OK status
        val endpoints = listOf(
            "/api/categories/random",
            "/api/tags/random",
            "/api/pets/random"
        )
        
        for (endpoint in endpoints) {
            val response = client.get(endpoint)
            assertEquals(HttpStatusCode.OK, response.status, "Endpoint $endpoint should return OK")
            // Just check that we get a JSON response
            val body = response.bodyAsText()
            assertTrue(body.startsWith("{"), "Response should be JSON")
            assertTrue(body.contains("\"id\":"), "Response should contain id field")
        }
        
        // Static resource test is limited to the API in this test
        // since we're not setting up the full application
    }
}