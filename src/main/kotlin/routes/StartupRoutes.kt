import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.startupRoutes(service: StartupService) {
    route("/api") {
        get("/status") { call.respond(service.state) }

        post("/marketing/run") {
            val result = service.runDailyMarketing()
            val report = DocumentGenerator.generateBusinessReport(service.state, result)
            DocumentGenerator.saveToDisk(DocumentGenerator.createZipWithReport(report), "kelnic_day_${service.state.day}.zip")
            call.respond(result)
        }

        post("/checkout") {
            val session = StripeService.createCheckoutSession(29900, "Kelnic Full Package", service.state.customerEmail)
            call.respond(mapOf("checkoutUrl" to session.url))
        }
    }
}
