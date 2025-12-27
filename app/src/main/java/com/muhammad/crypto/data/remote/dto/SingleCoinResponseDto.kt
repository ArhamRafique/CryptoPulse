package com.muhammad.crypto.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SingleCoinResponseDto(
    val data : CoinDto
)
