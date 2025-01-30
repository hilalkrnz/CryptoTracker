package com.hilalkara.cryptotracker.domain

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
    suspend fun register(email: String, password: String): Result<Unit>
    fun logout()
    fun getCurrentUser(): FirebaseUser?
    fun isUserLoggedIn(): Boolean

}