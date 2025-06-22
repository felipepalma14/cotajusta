package com.felipepalma14.cotajusta.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fiis")
data class FiiEntity(
    @PrimaryKey val code: String, // symbol in Fii
    val name: String,
    val change: Double,
    val closingPrice: Double,
    val dividendYield: Double,
    val high: Double,
    val lastPrice: Double,
    val lastYearHigh: Double,
    val lastYearLow: Double,
    val low: Double,
    val priceOpen: Double,
    val segment: String,
    val shares: Long,
    val targetAudience: String,
    val typeOfManagement: String,
    val volume: Double,
    val volumeAvg: Double,
    val isFavorite: Boolean = false
)
