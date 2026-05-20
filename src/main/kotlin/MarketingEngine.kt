import kotlin.random.Random
import kotlinx.serialization.Serializable
import java.time.LocalTime
import java.time.DayOfWeek

@Serializable
data class MarketingResult(
    val impressions: Int,
    val clicks: Int,
    val conversions: Int,
    val revenue: Int,
    val message: String,
    val postsPublished: Map<String, Boolean> = emptyMap(),
    val complianceScore: Double = 100.0,
    val warnings: List<String> = emptyList(),
    val trendAnalysis: TrendAnalysis
)

data class TrendAnalysis(
    val engagementRate: Double,
    val topPlatform: String,
    val recommendation: String
)

class MarketingEngine {

    private fun generateCompliantPost(state: BusinessState): String {
        return "We're building honest tools to make everyday shopping simpler and more transparent. " +
               "Real value, no hype. Progress update from our team."
    }

    fun runDailyCampaign(state: BusinessState): MarketingResult {
        if (!state.marketingEngineEnabled) {
            return MarketingResult(0, 0, 0, 0, "Marketing engine not activated.", trendAnalysis = TrendAnalysis(0.0, "", ""))
        }

        val impressions = 5200 + Random.nextInt(-900, 1400)
        val clickRate = 0.032 + (if (state.websiteCreated) 0.015 else 0.0)
        val conversionRate = when {
            state.checkoutEnabled -> 0.041
            state.shoppingCartEnabled -> 0.024
            else -> 0.011
        }

        val clicks = (impressions * clickRate).toInt()
        val conversions = (clicks * conversionRate).toInt()
        val revenue = conversions * (if (state.checkoutEnabled) 89 else 45)

        val engagementRate = if (clicks > 0) (conversions.toDouble() / clicks) * 100 else 0.0
        val trend = TrendAnalysis(
            engagementRate,
            if (state.checkoutEnabled) "Instagram" else "X",
            if (engagementRate > 3.5) "Strong performance - scale content" else "Test new creatives and landing page"
        )

        val compliance = ComplianceManager.checkCompliance(generateCompliantPost(state))

        val message = buildString {
            append("📣 Compliant Daily Pull Marketing Campaign (SA + Global)\n\n")
            append("Impressions: $impressions | Clicks: $clicks | Purchases: $conversions\n")
            append("Revenue: R$revenue\n")
            append("Compliance Score: ${compliance.score}%\n")
            append("Trend: ${trend.recommendation}")
        }

        return MarketingResult(impressions, clicks, conversions, revenue, message, 
            mapOf("X" to true, "Instagram" to true), compliance.score, compliance.warnings, trend)
    }
}
