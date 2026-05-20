import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import java.util.Date

object Auth {
    private const val SECRET = "kelnic-super-secret-jwt-key-2026-change-in-production"
    private val algorithm = Algorithm.HMAC256(SECRET)

    fun generateToken(userId: String, email: String): String {
        return JWT.create()
            .withClaim("userId", userId)
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + 7 * 86400000)) // 7 days
            .sign(algorithm)
    }

    fun configureAuthentication(application: Application) {
        application.install(Authentication) {
            jwt("auth-jwt") {
                verifier(JWT.require(algorithm).build())
                validate { credential ->
                    if (credential.payload.getClaim("userId").asString() != null) {
                        JWTPrincipal(credential.payload)
                    } else null
                }
            }
        }
    }
}
