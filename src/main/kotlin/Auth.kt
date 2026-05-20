import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class User(val id: String = "kelnic", val email: String)

object Auth {
    private const val SECRET = "your-super-secret-jwt-key-change-in-production" // Use env var in prod
    private val algorithm = Algorithm.HMAC256(SECRET)

    fun generateToken(user: User): String {
        return JWT.create()
            .withClaim("id", user.id)
            .withClaim("email", user.email)
            .withExpiresAt(Date(System.currentTimeMillis() + 86400000)) // 24h
            .sign(algorithm)
    }

    fun configureAuthentication(application: Application) {
        application.install(Authentication) {
            jwt("auth-jwt") {
                verifier(JWT.require(algorithm).build())
                validate { credential ->
                    if (credential.payload.getClaim("id").asString() != null) JWTPrincipal(credential.payload) else null
                }
            }
        }
    }
}
