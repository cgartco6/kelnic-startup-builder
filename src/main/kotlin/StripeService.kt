import com.stripe.Stripe
import com.stripe.model.checkout.Session
import com.stripe.param.checkout.SessionCreateParams

object StripeService {
    init {
        Stripe.apiKey = System.getenv("STRIPE_SECRET_KEY") ?: "sk_test_your_key_here"
    }

    fun createCheckoutSession(amountCents: Long, productName: String, email: String?): Session {
        val params = SessionCreateParams.builder()
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl("http://localhost:8080/success")
            .setCancelUrl("http://localhost:8080/cancel")
            .addLineItem(
                SessionCreateParams.LineItem.builder()
                    .setQuantity(1)
                    .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("usd")
                            .setUnitAmount(amountCents)
                            .setProductData(
                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                    .setName(productName)
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
            .setCustomerEmail(email)
            .build()

        return Session.create(params)
    }
}
