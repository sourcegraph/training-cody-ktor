package com.sourcegraph.petstore.routes

import com.sourcegraph.petstore.model.Category
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

// In-memory store for categories
private val categoryStore = ConcurrentHashMap<Long, Category>()
private val idCounter = AtomicLong(1)
private val random = Random()

private val petCategories = listOf(
    "Dog", "Cat", "Bird", "Fish", "Reptile", "Amphibian", "Small Mammal",
    "Exotic", "Farm Animal", "Insect", "Arachnid"
)

fun Route.categoryRoutes() {
    route("/categories") {
        // Get all categories
        get {
            call.respond(categoryStore.values.toList())
        }
        
        // Get category by ID
        get("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
            
            val category = categoryStore[id]
                ?: return@get call.respond(HttpStatusCode.NotFound, "Category not found")
            
            call.respond(category)
        }
        
        // Create new category
        post {
            val category = call.receive<Category>()
            val id = idCounter.getAndIncrement()
            category.id = id
            categoryStore[id] = category
            call.respond(HttpStatusCode.Created, category)
        }
        
        // Update category
        put("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
            
            val category = call.receive<Category>()
            category.id = id
            categoryStore[id] = category
            call.respond(category)
        }
        
        // Delete category
        delete("/{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID format")
            
            if (categoryStore.remove(id) != null) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound, "Category not found")
            }
        }
        
        // Get random category
        get("/random") {
            call.respond(generateRandomCategory())
        }
        
        // Get multiple random categories
        get("/random/{count}") {
            val count = call.parameters["count"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid count format")
            
            val randomCategories = List(count) { generateRandomCategory() }
            call.respond(randomCategories)
        }
    }
}

private fun generateRandomCategory(): Category {
    return Category(
        id = random.nextLong(100),
        name = petCategories[random.nextInt(petCategories.size)]
    )
}