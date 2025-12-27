package com.muhammad.crypto.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muhammad.crypto.presentation.screens.coin_details.CoinDetailScreen
import com.muhammad.crypto.presentation.screens.favourite_coins.FavouriteCoinsScreen
import com.muhammad.crypto.presentation.screens.home.HomeScreen

@Composable
fun AppNavigation(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = Destinations.HomeScreen){
        composable<Destinations.HomeScreen>{
            HomeScreen(navHostController = navHostController)
        }
        composable<Destinations.FavouriteCoinsScreen>{
            FavouriteCoinsScreen(navHostController = navHostController)
        }
        composable<Destinations.CoinDetailsScreen>{
            CoinDetailScreen(navHostController = navHostController)
        }
    }
}