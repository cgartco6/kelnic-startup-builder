// User.kt
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val email: String,
    val tier: SubscriptionTier = SubscriptionTier.FREE,
    val businessState: BusinessState = BusinessState()
)

enum class SubscriptionTier {
    FREE, STARTER, PRO, ENTERPRISE
}
