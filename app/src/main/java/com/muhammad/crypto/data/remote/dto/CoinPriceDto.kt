package com.muhammad.crypto.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceDto(
    val priceUsd : Double,
    val time : Long
)