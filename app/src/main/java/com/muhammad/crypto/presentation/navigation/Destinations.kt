package com.muhammad.crypto.presentation.navigation

import kotlinx.serialization.Serializable

interface Destinations {
    @Serializable
    data object HomeScreen : Destinations
    @Serializable
    data class CoinDetailsScreen(val id : String) : Destinations
    @Serializable
    data object FavouriteCoinsScreen : Destinations
}