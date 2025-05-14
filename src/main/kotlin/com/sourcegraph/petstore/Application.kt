package com.sourcegraph.petstore

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.sourcegraph.petstore.routes.categoryRoutes
import com.sourcegraph.petstore.routes.petRoutes
import com.sourcegraph.petstore.routes.tagRoutes
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.http.*
import io.ktor.server.response.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT")?.toIntOrNull() ?: 8080, host = "0.0.0.0") {
        configureRouting()
    }.start(wait = true)
}

fun Application.configureRouting() {
    // Configure JSON serialization with Jackson
    install(ContentNegotiation) {
        jackson {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            registerModule(JavaTimeModule())
        }
    }
    
    // Configure CORS
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        anyHost()
    }
    
    // Simple authentication - not required for demo
    install(Authentication) {
        // Configuration is empty as we want all routes to be accessible
    }
    
    // Configure routing
    routing {
        // API routes
        route("/api") {
            categoryRoutes()
            petRoutes()
            tagRoutes()
        }
        
        // Static file routing for SPA
        staticResources("/", "static") {
            default("index.html")
        }
    }
}