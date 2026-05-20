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
    val postsPublished: Map<String, Boolean>,
    val complianceScore: Double,
    val warnings: List<String>,
    val trendAnalysis: TrendAnalysis,
    val landingPageUrl: String = "https://kelnic.co.za/impulse"
)

data class TrendAnalysis(val engagementRate: Double, val topPlatform: String, val recommendation: String)

class MarketingEngine {

    private fun generateProfessionalAdCopy(): String {
        return "Discover smart daily essentials that actually save you time and money. " +
               "Curated products with transparent pricing and fast South African delivery. " +
               "No gimmicks — just better shopping."
    }

    private fun generateLandingPageDescription(state: BusinessState): String {
        return if (state.checkoutEnabled) {
            "Professional, mobile-first landing page with real shopping cart, secure checkout, and impulse buy optimization."
        } else {
            "High-converting landing page with clear value proposition and strong call-to-action."
        }
    }

    fun runDailyCampaign(state: BusinessState): MarketingResult {
        if (!state.marketingEngineEnabled) {
            return MarketingResult(0, 0, 0, 0, "Marketing not active", emptyMap(), 0.0, emptyList(), TrendAnalysis(0.0,"",""))
        }

        // Aggressive South Africa impulse buyer targeting
        val baseImpressions = 45_000 + Random.nextInt(-5_000, 12_000)

        val clickRate = when {
            state.checkoutEnabled && state.webAppCreated -> 0.085
            state.websiteCreated -> 0.062
            else -> 0.035
        }

        val conversionRate = when {
            state.checkoutEnabled -> 0.068   // Strong with professional checkout
            state.shoppingCartEnabled -> 0.041
            else -> 0.018
        }

        val impressions = baseImpressions
        val clicks = (impressions * clickRate).toInt()
        val conversions = (clicks * conversionRate).toInt()

        // Rapid revenue scaling
        val avgOrderValue = when {
            state.checkoutEnabled && state.mobileAppCreated -> 185
            state.checkoutEnabled -> 135
            state.webAppCreated -> 95
            else -> 65
        }

        val revenue = conversions * avgOrderValue

        val engagementRate = if (clicks > 0) (conversions.toDouble() / clicks) * 100 else 0.0

        val trend = TrendAnalysis(
            engagementRate,
            "Instagram + X",
            if (revenue > 8000) "Excellent scaling - maintain momentum" else "Scale budget on high-conversion platforms"
        )

        val compliance = ComplianceManager.checkCompliance(generateProfessionalAdCopy())

        val message = buildString {
            append("🚀 AGGRESSIVE COMPLIANT PULL MARKETING CAMPAIGN (South Africa)\n\n")
            append("Impressions: ${impressions.formatWithCommas()}\n")
            append("Clicks to professional landing page: ${clicks.formatWithCommas()}\n")
            append("Conversions: $conversions\n")
            append("Revenue Today: R$revenue\n")
            append("Avg Order Value: R$avgOrderValue\n")
            append("Compliance Score: ${compliance.score}%")
        }

        return MarketingResult(
            impressions, clicks, conversions, revenue, message,
            mapOf("Instagram" to true, "X" to true, "Facebook" to true),
            compliance.score, compliance.warnings, trend,
            landingPageUrl = "https://kelnic.co.za/impulse-${state.day}"
        )
    }

    private fun Int.formatWithCommas(): String = "%,d".format(this)
}
