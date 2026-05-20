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

    // Background Real Scheduler
    GlobalScope.launch(Dispatchers.Default) {
        while (true) {
            delay(Duration.between(LocalTime.now(), LocalTime.of(8, 0)).toMillis() + 86400000)
            if (service.state.marketingEngineEnabled) {
                val result = service.runDailyMarketing()
                PaymentService.processPayout(result.revenue)
            }
        }
    }

    routing {
        startupRoutes(service)
    }
}
