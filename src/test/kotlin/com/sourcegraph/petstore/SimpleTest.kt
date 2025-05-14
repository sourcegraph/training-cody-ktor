package com.sourcegraph.petstore

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * Simple test class that just verifies the endpoints return HTTP 200
 */
class SimpleTest {
    
    @Test
    fun testEndpointsRespond() {
        testApplication {
            // Set up a minimal test environment
            val client = createClient {
                expectSuccess = false
            }
            
            // Test that API endpoints respond
            val endpoints = listOf(
                "/api/categories/random",
                "/api/pets/random",
                "/api/tags/random"
            )
            
            for (endpoint in endpoints) {
                val response = client.get(endpoint)
                assertEquals(HttpStatusCode.OK, response.status, "Endpoint $endpoint should return OK")
            }
        }
    }
}