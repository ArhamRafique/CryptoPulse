package com.muhammad.crypto.domain.model.favourite

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

@Immutable
data class FavouriteCoin(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    @get:DrawableRes val iconRes: Int,
)
