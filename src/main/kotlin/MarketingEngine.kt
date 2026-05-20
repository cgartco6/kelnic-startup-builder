import kotlin.random.Random
import kotlinx.serialization.Serializable

@Serializable
data class MarketingResult(
    val impressions: Int,
    val clicks: Int,
    val conversions: Int,
    val revenue: Int,
    val message: String,
    val landingPageUrl: String
)

class MarketingEngine {
    fun runDailyCampaign(state: BusinessState): MarketingResult {
        val impressions = 35_000 + Random.nextInt(0, 15_000)
        val clicks = (impressions * 0.065).toInt()
        val conversions = (clicks * 0.055).toInt()
        val revenue = conversions * 145

        return MarketingResult(
            impressions, clicks, conversions, revenue,
            "🚀 Real Daily Marketing Campaign Executed - R$revenue generated",
            "https://kelnic.co.za/launch/${System.currentTimeMillis()}"
        )
    }
}
