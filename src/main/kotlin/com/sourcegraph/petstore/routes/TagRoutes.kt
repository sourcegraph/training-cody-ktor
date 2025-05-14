package com.sourcegraph.petstore.routes

import com.sourcegraph.petstore.model.Tag
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

// In-memory store for tags
private val tagStore = ConcurrentHashMap<Long, Tag>()
private val idCounter = AtomicLong(1)
private val random = Random()

private val commonTags = listOf(
    "Friendly", "Active", "Playful", "Shy", "Trained", "Vaccinated",
    "Neutered", "Spayed", "Senior", "Puppy", "Kitten", "Rare",
    "Exotic", "Hypoallergenic", "Special Needs", "Rescue"
)

fun Route.tagRoutes() {
    route("/tags") {
        // Get all tags
        get {
            call.respond(tagStore.values.toList())
        }
        
        // Get tag by ID
        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
            
            val tag = tagStore[id]
                ?: return@get call.respond(HttpStatusCode.NotFound, "Tag not found")
            
            call.respond(tag)
        }
        
        // Create new tag
        post {
            val tag = call.receive<Tag>()
            val id = idCounter.getAndIncrement()
            tag.id = id
            tagStore[id] = tag
            call.respond(HttpStatusCode.Created, tag)
        }
        
        // Update tag
        put("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
            
            val tag = call.receive<Tag>()
            tag.id = id
            tagStore[id] = tag
            call.respond(tag)
        }
        
        // Delete tag
        delete("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
            
            if (tagStore.remove(id) != null) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, "Tag not found")
            }
        }
        
        // Get random tag
        get("/random") {
            call.respond(generateRandomTag())
        }
        
        // Get multiple random tags
        get("/random/{count}") {
            val count = call.parameters["count"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid count format")
            
            val randomTags = List(count) { generateRandomTag() }
            call.respond(randomTags)
        }
    }
}

private fun generateRandomTag(): Tag {
    return Tag(
        id = random.nextLong(1000),
        name = commonTags[random.nextInt(commonTags.size)]
    )
}