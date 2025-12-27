package com.muhammad.crypto.domain.model.coin

import androidx.compose.runtime.Immutable

@Immutable
data class Coin(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val maxSupply : Double?=null,
    val volumeUsd24Hr : Double,
    val vwap24Hr : Double,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double
)