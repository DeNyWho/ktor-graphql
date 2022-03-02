package com.example.services

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.models.User
import com.example.models.UserInput
import com.example.models.UserResponse
import com.example.repository.UserRepository
import com.mongodb.client.MongoClient
import io.ktor.application.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.nio.charset.StandardCharsets
import java.util.*

class AuthService: KoinComponent {

    private val client: MongoClient by inject()
    private val repo: UserRepository = UserRepository(client)
    private val secret: String = "secret"
    private val algorithm: Algorithm = Algorithm.HMAC256(secret)
    private val verifier: JWTVerifier = JWT.require(algorithm).build()

    // sign in - returns a user response if successful

    fun signIn(userInput: UserInput): UserResponse? {
        val user = repo.getUserByEmail(userInput.email) ?: error("No such user by that email")
        // hash incoming password and compare it to the saved hashed password
        if ( BCrypt.verifyer().verify(
                userInput.password.toByteArray(Charsets.UTF_8),
                user.hashPass
            ).verified) {
            val token = signAccessToken(user.id)
            return UserResponse(token, user)
        }
        error("password is incorrect")
    }

    private fun signAccessToken(id: String): String{
        return JWT.create()
            .withIssuer("example")
            .withClaim("userId", id)
            .sign(algorithm)
    }

    // sign up - return a user response if successful (email does not already exist)

    fun signUp(userInput: UserInput): UserResponse? {
        val hashedPassword = BCrypt
            .withDefaults()
            .hash(10, userInput.password.toByteArray(StandardCharsets.UTF_8))
        val id = UUID.randomUUID().toString()
        val emailUser = repo.getUserByEmail(userInput.email)
        // check if existing user exists
        if (emailUser != null) {
            error ("Email already in use")
        }
        val newUser = repo.add(
            User(
                id = id,
                email = userInput.email,
                hashPass = hashedPassword
            )
        )
        val token = signAccessToken(newUser.id)
        return UserResponse(token, newUser)
    }

    // verifyToken - check an existing token and return a user model if token is valid
    fun verifyToken(call: ApplicationCall): User? {
        return try {
            val authHeader = call.request.headers["Authorization"] ?: ""
            val token = authHeader.split("Bearer ").last()
            // Authorization: Bearer <token>
            val accessToken = verifier.verify(JWT.decode(token))
            val userId = accessToken.getClaim("userId").asString()
            return User(
                id = userId,
                email = "",
                hashPass = ByteArray(0)
            )
        } catch (e: Exception) {
            null
        }
    }


}