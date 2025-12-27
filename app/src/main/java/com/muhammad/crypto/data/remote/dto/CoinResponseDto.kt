package com.muhammad.crypto.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinResponseDto(
    val data : List<CoinDto>
)