class StartupService {
    var currentUser: User? = null

    fun upgradeTier(tierStr: String?) {
        val tier = when (tierStr?.lowercase()) {
            "starter" -> SubscriptionTier.STARTER
            "pro" -> SubscriptionTier.PRO
            "enterprise" -> SubscriptionTier.ENTERPRISE
            else -> SubscriptionTier.FREE
        }
        currentUser = currentUser?.copy(tier = tier)
        println("✅ User upgraded to ${tier.name}")
    }

    suspend fun runDailyMarketing(): MarketingResult {
        val result = MarketingEngine().runDailyCampaign(currentUser?.businessState ?: BusinessState())
        // Generate real report
        val report = DocumentGenerator.generateBusinessReport(currentUser?.businessState ?: BusinessState(), result)
        DocumentGenerator.saveToDisk(DocumentGenerator.createZipWithReport(report), "reports/kelnic_report_${System.currentTimeMillis()}.zip")
        return result
    }

    // Add all step methods (generateIdea, createWebsite, etc.) from previous versions
}
