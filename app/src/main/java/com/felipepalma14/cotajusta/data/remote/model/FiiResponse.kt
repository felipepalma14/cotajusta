package com.felipepalma14.cotajusta.data.remote.model

data class FiiResponse(
    val fiis: List<Fii>
)

data class Fii(
    val change: Double,
    val closingPrice: Double,
    val dividendYield: Double,
    val high: Double,
    val lastPrice: Double,
    val lastYearHigh: Double,
    val lastYearLow: Double,
    val low: Double,
    val name: String,
    val priceOpen: Double,
    val segment: String,
    val shares: Long,
    val symbol: String,
    val targetAudience: String,
    val typeOfManagement: String,
    val volume: Double,
    val volumeAvg: Double
)
