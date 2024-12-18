package com.zybooks.practicepals.database.entities

import androidx.room.Entity

@Entity(tableName = "users")
data class User(
    val userId: String = "",
    val username: String = "",
    val email: String = "",
    val profilePictureUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
