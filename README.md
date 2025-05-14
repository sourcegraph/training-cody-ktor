# Ktor Pet Store API

This is a Ktor implementation of the Pet Store API using Kotlin. It provides the same functionality as the Spring Boot and Quarkus implementations but uses Ktor and Kotlin instead.

## Requirements

- JDK 17 or higher
- Gradle 8.x

## Building and Running

You can build and run the application using Gradle:

```bash
./gradlew run
```

This will start the application in development mode. The API will be accessible at http://localhost:8080/api

## Features

- RESTful API for pets, categories, and tags implemented with Ktor routing
- Custom Kotlin model classes
- JSON serialization with Jackson
- SPA support with static resource serving
- Modern Kotlin coroutines for asynchronous programming

## API Endpoints

- `/api/pets/random` - Get a random pet
- `/api/pets/random/{count}` - Get multiple random pets
- `/api/categories/random` - Get a random category
- `/api/categories/random/{count}` - Get multiple random categories
- `/api/tags/random` - Get a random tag
- `/api/tags/random/{count}` - Get multiple random tags

The application also supports standard CRUD operations on categories and tags.

## Building a Distributable Package

To build a distributable package:

```bash
./gradlew build
```

The distribution files will be available in the `build/distributions` directory. You can unzip the distribution and run the application using the provided start scripts.

## Running Tests

To run the tests:

```bash
./gradlew test
```