package com.sourcegraph.petstore.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Category(
    @JsonProperty("id")
    var id: Long? = null,
    
    @JsonProperty("name")
    var name: String? = null
)

data class Tag(
    @JsonProperty("id")
    var id: Long? = null,
    
    @JsonProperty("name")
    var name: String? = null
)

data class Pet(
    @JsonProperty("id")
    var id: Long? = null,
    
    @JsonProperty("name")
    var name: String? = null,
    
    @JsonProperty("category")
    var category: Category? = null,
    
    @JsonProperty("photoUrls")
    var photoUrls: List<String> = emptyList(),
    
    @JsonProperty("tags")
    var tags: List<Tag>? = null,
    
    @JsonProperty("status")
    var status: Status? = null
) {
    enum class Status {
        @JsonProperty("available")
        AVAILABLE,
        
        @JsonProperty("pending")
        PENDING,
        
        @JsonProperty("sold")
        SOLD
    }
}