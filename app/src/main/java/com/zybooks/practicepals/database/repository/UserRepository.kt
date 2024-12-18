package com.zybooks.practicepals.database.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.zybooks.practicepals.database.entities.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    suspend fun createUser(email: String, password: String, username: String): Result<User> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()

            val user = User(
                userId = authResult.user?.uid ?: "",
                username = username,
                email = email
            )

            firestore.collection("users")
                .document(user.userId)
                .set(user)
                .await()

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signIn(email: String, password: String): Result<User> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            val userDoc = firestore.collection("users")
                .document(authResult.user?.uid ?: "")
                .get()
                .await()

            Result.success(userDoc.toObject(User::class.java) ?: throw Exception("User not found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun uploadProfilePicture(userId: String, imageUri: Uri): Result<String> {
        return try {
            val storageRef = storage.reference.child("profile_pictures/$userId.jpg")
            val uploadTask = storageRef.putFile(imageUri).await()
            val downloadUrl = uploadTask.storage.downloadUrl.await().toString()

            firestore.collection("users")
                .document(userId)
                .update("profilePictureUrl", downloadUrl)
                .await()

            Result.success(downloadUrl)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
