post("/payments/stripe") { /* ... */ }
post("/payments/payfast") { call.respond(mapOf("url" to PaymentService.createPayFastPayment(299.0, "Kelnic Package", "customer@email.com"))) }
post("/payments/ozow") { /* similar */ }
post("/payments/eft") { call.respondText(PaymentService.getEFTDetails(299.0)) }
post("/investor/demo") { service.toggleInvestorDemoMode(); call.respond(service.state) }
