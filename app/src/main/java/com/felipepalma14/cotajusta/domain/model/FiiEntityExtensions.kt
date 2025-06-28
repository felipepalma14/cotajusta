package com.felipepalma14.cotajusta.domain.model

import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import com.felipepalma14.cotajusta.data.remote.model.Fii

fun FiiEntity.toFii(): Fii = Fii(
    symbol = code,
    change = change,
    closingPrice = closingPrice,
    dividendYield = dividendYield,
    high = high,
    lastPrice = lastPrice,
    lastYearHigh = lastYearHigh,
    lastYearLow = lastYearLow,
    low = low,
    name = name,
    priceOpen = priceOpen,
    segment = segment,
    shares = shares,
    targetAudience = targetAudience,
    typeOfManagement = typeOfManagement,
    volume = volume,
    volumeAvg = volumeAvg
)
