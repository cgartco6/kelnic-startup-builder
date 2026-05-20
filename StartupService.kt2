fun generateDailyRevenue(result: MarketingResult): Int {
    return if (state.isInvestorDemoMode) {
        Random.nextInt(12000, 25000)  // Boosted for demos
    } else {
        (result.revenue * 1.15).toInt()   // 15% uplift from real marketing
    }
}
