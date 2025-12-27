package com.muhammad.crypto.presentation.screens.favourite_coins

import com.muhammad.crypto.domain.model.favourite.FavouriteCoin

data class FavouriteCoinState(
    val favouriteCoins : List<FavouriteCoin> = emptyList()
)
