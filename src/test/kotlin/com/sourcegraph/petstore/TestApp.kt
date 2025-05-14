package com.sourcegraph.petstore

import com.sourcegraph.petstore.routes.categoryRoutes
import com.sourcegraph.petstore.routes.petRoutes
import com.sourcegraph.petstore.routes.tagRoutes
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// This is a simplified test configuration with minimal setup
fun Application.testModule() {
    // No ContentNegotiation installation to avoid duplicate plugin exception
    
    routing {
        route("/api") {
            categoryRoutes()
            petRoutes()
            tagRoutes()
        }
        
        // Basic static content for tests
        get("/") {
            call.respondText("Test Static Content", ContentType.Text.Html)
        }
    }
}