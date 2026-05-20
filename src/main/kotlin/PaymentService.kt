object PaymentService {
    fun createPayFastLink(amount: Double) = "https://payfast.co.za/real-link?amount=$amount"
    fun createOzowLink(amount: Double) = "https://ozow.com/pay?amount=$amount"
    fun getEFTDetails(amount: Double) = "FNB Account • Ref: KEL${System.currentTimeMillis()}"
}
