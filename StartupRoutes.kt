import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.startupRoutes(service: StartupService) {
    route("/api") {
        post("/register") { /* register logic */ call.respond(mapOf("status" to "success")) }

        authenticate("auth-jwt") {
            get("/status") { call.respond(service.currentUser ?: "No user") }

            post("/upgrade/{tier}") {
                val tier = call.parameters["tier"]
                service.upgradeTier(tier)
                call.respond(service.currentUser!!)
            }

            post("/marketing/run") { call.respond(service.runDailyMarketing()) }
        }
    }
}
