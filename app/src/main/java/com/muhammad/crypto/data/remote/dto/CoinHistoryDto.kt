package com.muhammad.crypto.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinHistoryDto(
    val data : List<CoinPriceDto>
)