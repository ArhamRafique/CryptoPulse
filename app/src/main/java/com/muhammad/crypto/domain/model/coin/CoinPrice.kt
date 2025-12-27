package com.muhammad.crypto.domain.model.coin

import androidx.compose.runtime.Immutable
import java.time.ZonedDateTime

@Immutable
data class CoinPrice(
    val priceUsd : Double,
    val datetime : ZonedDateTime
)