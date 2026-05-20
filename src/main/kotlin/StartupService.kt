class StartupService {
    var state = BusinessState()
    private val marketingEngine = MarketingEngine()

    // All step methods (same as before)
    fun generateIdea() = update { ideaGenerated = true } to "🚀 Revolutionary idea generated!"
    fun researchAndPlanning() = check({ ideaGenerated }) { researched = true } to "📊 Research completed."
    fun createWebsite() = check({ researched }) { websiteCreated = true } to "🌐 Website created."
    fun createWebApp() = check({ websiteCreated }) { webAppCreated = true } to "💻 Web App created."
    fun createMobileApp() = check({ webAppCreated }) { mobileAppCreated = true } to "📱 Mobile App created."
    fun enableMarketing() = check({ mobileAppCreated }) { marketingEngineEnabled = true } to "📣 Compliant Pull Marketing Engine activated."
    fun enableShoppingCart() = check({ webAppCreated }) { shoppingCartEnabled = true } to "🛒 Shopping cart enabled."
    fun enableCheckout() = check({ shoppingCartEnabled }) { checkoutEnabled = true } to "💳 Stripe checkout enabled."

    suspend fun runDailyMarketing() = marketingEngine.runDailyCampaign(state)

    private fun update(block: BusinessState.() -> Unit): Boolean {
        block(state); return true
    }
    private fun check(cond: BusinessState.() -> Boolean, block: BusinessState.() -> Unit): Boolean {
        if (!state.cond()) return false
        block(state); return true
    }
}
