package com.zybooks.practicepals.database.dao

import com.zybooks.practicepals.database.entities.User

interface UserDao {
    suspend fun createUser(user: User)
    suspend fun getUser(id: String): User?
    suspend fun updateUser(user: User)
    suspend fun updateProfilePicture(userId: String, pictureUrl: String)
}
