import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import routes.startupRoutes
import java.time.Duration
import java.time.LocalTime
import java.time.temporal.ChronoUnit

fun main() {
    embeddedServer(Netty, port = 8080) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) { json() }

    val service = StartupService()
    val marketingEngine = MarketingEngine() // Direct access for scheduler

    // === BACKGROUND DAILY SCHEDULER ===
    GlobalScope.launch(Dispatchers.Default) {
        while (true) {
            val now = LocalTime.now()
            val nextRun = if (now.hour >= 8) {
                now.plus(1, ChronoUnit.DAYS).withHour(8).withMinute(0)
            } else {
                now.withHour(8).withMinute(0)
            }
            val delayMillis = Duration.between(now, nextRun).toMillis()

            delay(delayMillis)

            if (service.state.marketingEngineEnabled) {
                println("\n🔄 [AUTO] Running Daily Marketing Campaign at 08:00")
                val result = marketingEngine.runDailyCampaign(service.state)
                
                val reportBytes = DocumentGenerator.generateBusinessReport(service.state, result)
                val zipBytes = DocumentGenerator.createZipWithReport(reportBytes)
                DocumentGenerator.saveToDisk(zipBytes, "reports/kelnic_auto_day_${service.state.day}.zip")
                
                service.state.day++
                service.state.totalRevenue += result.revenue.toLong()
                
                println("✅ Auto Campaign Complete - Revenue Today: R${result.revenue}")
            }
        }
    }

    routing {
        startupRoutes(service)
        get("/") { call.respondText("Kelnic Startup Builder API Running - Compliant + Auto Scheduler Active") }
        get("/success") { call.respondText("Payment Successful! Thank you.") }
    }
}
