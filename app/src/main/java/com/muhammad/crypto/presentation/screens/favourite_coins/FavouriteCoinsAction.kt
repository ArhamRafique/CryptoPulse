package com.muhammad.crypto.presentation.screens.favourite_coins

sealed interface FavouriteCoinsAction {
    data class OnDeleteFavouriteCoin(val id: String) : FavouriteCoinsAction
}
