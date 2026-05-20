import java.time.LocalDate

data class ComplianceResult(
    val score: Double,
    val warnings: List<String>,
    val applicableRules: List<String>
)

object ComplianceManager {
    private val saRules = listOf(
        "Complies with Consumer Protection Act (CPA) 2026",
        "No misleading or deceptive marketing",
        "Honest value propositions only",
        "POPIA compliant data handling",
        "Respect National Opt-Out Registry",
        "Clear commercial content identification"
    )

    fun checkCompliance(content: String): ComplianceResult {
        val warnings = mutableListOf<String>()
        var score = 100.0

        val lower = content.lowercase()
        if (lower.contains("today only") || lower.contains("limited time") || lower.contains("guaranteed")) {
            warnings.add("Prohibited urgency language - CPA violation")
            score -= 45
        }
        if (lower.contains("best in world") || lower.contains("number one")) {
            warnings.add("Risk of misleading comparative claim")
            score -= 20
        }

        return ComplianceResult(score, warnings, saRules)
    }
}
