package com.example.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.algorithms.Algorithm
import com.example.models.UserInput
import com.example.models.UserResponse
import com.example.repository.UserRepository
import com.mongodb.client.MongoClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthService: KoinComponent {

    private val client: MongoClient by inject()
    private val repo: UserRepository = UserRepository(client)
    private val secret: String = "secret"
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)

    // sign in - returns a user response if successful

    fun signIn(userInput: UserInput): UserResponse? {
        val user = repo.getUserByEmail(userInput.email) ?: error("No such user by that email")
        // hash incoming password and compare it to the saved hashed password
        if ( BCrypt.verifyer().verify(
                userInput.password.toByteArray(Charsets.UTF_8),
                user.hashPass
            ).verified) {
            // TODO: Finish signAccessToken
//            val token = signAccessToken(user.id)
//            return UserResponse(token, user)
        }
        error("password is incorrect")
    }

    private fun signAccessToken(id: String): Any {

    }
    // sign up - return a user response if successful (email does not already exist)
    // signAccessToken - sign and return a valid auth token
    // verifyToken - check an existing token and return a user model if token is valid


}