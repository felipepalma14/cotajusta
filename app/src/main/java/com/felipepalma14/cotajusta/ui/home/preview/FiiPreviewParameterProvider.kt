package com.felipepalma14.cotajusta.ui.home.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.felipepalma14.cotajusta.data.remote.model.Fii

class FiiPreviewParameterProvider : PreviewParameterProvider<Fii> {
    override val values = sequenceOf(
        Fii(
            symbol = "HGLG11",
            name = "CSHG Logística",
            lastPrice = 100.0,
            dividendYield = 7.5,
            change = 0.5,
            segment = "Logística",
            volume = 1000000.0,
            volumeAvg = 950000.0,
            priceOpen = 99.0,
            high = 101.0,
            low = 98.0,
            lastYearHigh = 120.0,
            lastYearLow = 90.0,
            closingPrice = 100.0,
            typeOfManagement = "Ativa",
            targetAudience = "Geral",
            shares = 1000000L
        ),
        Fii(
            symbol = "GGRC11",
            name = "GGRC11 Logística",
            lastPrice = 10.0,
            dividendYield = 7.5,
            change = 0.5,
            segment = "Logística",
            volume = 1000000.0,
            volumeAvg = 950000.0,
            priceOpen = 99.0,
            high = 101.0,
            low = 98.0,
            lastYearHigh = 10.0,
            lastYearLow = 9.0,
            closingPrice = 10.0,
            typeOfManagement = "Ativa",
            targetAudience = "Geral",
            shares = 1200000L
        )
    )
}
