package com.example.firebase_test.data.model

data class Movie(
    val name: String? = null,
    val price: Float? = null,
    val category: MutableList<String>? = null
)