package ru.alexeyk2021.taskplanner

data class User(
    val id: Int,
    val email: String,
    val password: String,
    val name: String,
    val tasks: List<Task>
)
