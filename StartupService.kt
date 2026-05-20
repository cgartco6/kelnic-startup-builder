class StartupService {
    var state = BusinessState()
    private val marketingEngine = MarketingEngine()

    fun toggleInvestorDemoMode() {
        state.isInvestorDemoMode = !state.isInvestorDemoMode
        if (state.isInvestorDemoMode) {
            state.totalRevenue = 1250000L  // Impressive number for investors
        }
    }

    suspend fun runDailyMarketing(): MarketingResult {
        val result = marketingEngine.runDailyCampaign(state)
        if (state.isInvestorDemoMode) {
            result.copy(revenue = result.revenue * 8) // Boosted for demo
        } else result
        // Auto generate investor PDF
        val report = DocumentGenerator.generateBusinessReport(state, result)
        DocumentGenerator.saveToDisk(DocumentGenerator.createZipWithReport(report), "reports/investor_day_${state.day}.zip")
        return result
    }

    // All other step functions (generateIdea, createWebsite, etc.) remain as before
}
