import com.stripe.model.checkout.Session

object PaymentService {

    // Stripe (International + Cards)
    fun createStripeSession(amountCents: Long, email: String?): Session {
        // ... (same as previous)
        return Session.create(/* params */)
    }

    // PayFast (South Africa)
    fun createPayFastPayment(amountZar: Double, itemName: String, email: String): String {
        return "https://www.payfast.co.za/eng/process?merchant_id=YOUR_ID&amount=$amountZar&item_name=$itemName&email=$email"
    }

    // Ozow (Instant EFT)
    fun createOzowPayment(amountZar: Double, email: String): String {
        return "https://ozow.com/pay?amount=$amountZar&email=\( email&transaction_id= \){System.currentTimeMillis()}"
    }

    // PayPal
    fun createPayPalPayment(amountZar: Double, email: String): String {
        return "https://www.paypal.com/pay?amount=$amountZar&currency=ZAR&email=$email"
    }

    // Manual EFT (FNB / African Bank)
    fun getEFTDetails(amountZar: Double): String {
        return """
            Bank: FNB
            Account: 6273 874 1234 (Kelnic)
            Reference: KEL${System.currentTimeMillis()}
            Amount: R$amountZar
            Please use exact reference for auto-reconciliation.
        """.trimIndent()
    }

    fun processPayout(revenueZar: Int): PayoutResult {
        val ownerShare = (revenueZar * 0.50).toInt()
        val reserve = revenueZar - ownerShare
        println("💰 50/50 SPLIT → Owner (FNB): R$ownerShare | Business Reserve: R$reserve")
        return PayoutResult(ownerShare, reserve, revenueZar)
    }
}

data class PayoutResult(val ownerAmount: Int, val reserveAmount: Int, val total: Int)
