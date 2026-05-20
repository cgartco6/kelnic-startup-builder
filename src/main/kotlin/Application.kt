import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import routes.startupRoutes
import java.time.*

fun main() {
    embeddedServer(Netty, port = 8080) { module() }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) { json() }
    Auth.configureAuthentication(this)

    val service = StartupService()

    // Background Daily Marketing
    GlobalScope.launch(Dispatchers.Default) {
        while (true) {
            delay(86400000) // 24 hours
            if (service.currentUser?.businessState?.marketingEngineEnabled == true) {
                service.runDailyMarketing()
            }
        }
    }

    routing { startupRoutes(service) }
}
