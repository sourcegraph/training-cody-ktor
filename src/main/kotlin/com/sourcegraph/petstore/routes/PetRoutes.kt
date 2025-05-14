package com.sourcegraph.petstore.routes

import com.sourcegraph.petstore.model.Category
import com.sourcegraph.petstore.model.Pet
import com.sourcegraph.petstore.model.Tag
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

private val random = Random()

private val petNames = listOf(
    "Buddy", "Max", "Charlie", "Lucy", "Cooper", "Bella", "Luna", "Daisy",
    "Rocky", "Sadie", "Milo", "Bailey", "Jack", "Oliver", "Chloe", "Pepper"
)

private val categories = listOf(
    "Dog", "Cat", "Bird", "Fish", "Reptile", "Rodent", "Exotic"
)

private val tagNames = listOf(
    "Friendly", "Playful", "Trained", "Young", "Adult", "Senior",
    "Vaccinated", "Neutered", "Spayed", "Rescue", "Purebred", "Hypoallergenic"
)

fun Route.petRoutes() {
    route("/pets") {
        // Get random pet
        get("/random") {
            call.respond(generateRandomPet())
        }
        
        // Get multiple random pets
        get("/random/{count}") {
            val count = call.parameters["count"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid count format")
            
            val randomPets = List(count) { generateRandomPet() }
            call.respond(randomPets)
        }
    }
}

private fun generateRandomPet(): Pet {
    // Create pet with required fields
    val pet = Pet(
        id = random.nextLong(10000),
        name = getRandomElement(petNames),
        photoUrls = generateRandomPhotoUrls()
    )

    // Add optional fields
    pet.category = generateRandomCategory()
    pet.tags = generateRandomTags()
    pet.status = getRandomStatus()

    return pet
}

private fun generateRandomCategory(): Category {
    return Category(
        id = random.nextLong(100),
        name = getRandomElement(categories)
    )
}

private fun generateRandomPhotoUrls(): List<String> {
    val count = random.nextInt(3) + 1 // 1-3 photos
    return List(count) { "https://example.com/pet-photos/${UUID.randomUUID()}.jpg" }
}

private fun generateRandomTags(): List<Tag> {
    val count = random.nextInt(4) // 0-3 tags
    return List(count) {
        Tag(
            id = random.nextLong(100),
            name = getRandomElement(tagNames)
        )
    }
}

private fun getRandomStatus(): Pet.Status {
    return Pet.Status.values()[random.nextInt(Pet.Status.values().size)]
}

private fun <T> getRandomElement(list: List<T>): T {
    return list[random.nextInt(list.size)]
}