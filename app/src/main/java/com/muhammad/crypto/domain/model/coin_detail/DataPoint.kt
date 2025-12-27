package com.muhammad.crypto.domain.model.coin_detail

import androidx.compose.runtime.Immutable

@Immutable
data class DataPoint(
    val x : Float,
    val y : Float,
    val xLabel : String
)