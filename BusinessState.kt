import kotlinx.serialization.Serializable

@Serializable
data class BusinessState(
    var ideaGenerated: Boolean = false,
    var researched: Boolean = false,
    var websiteCreated: Boolean = false,
    var webAppCreated: Boolean = false,
    var mobileAppCreated: Boolean = false,
    var marketingEngineEnabled: Boolean = false,
    var shoppingCartEnabled: Boolean = false,
    var checkoutEnabled: Boolean = false,
    var totalRevenue: Long = 0L,
    var day: Int = 1
)
