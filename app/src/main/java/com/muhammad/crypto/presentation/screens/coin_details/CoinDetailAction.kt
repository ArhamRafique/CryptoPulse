package com.muhammad.crypto.presentation.screens.coin_details

sealed interface CoinDetailAction{
    data object GetCoinDetails : CoinDetailAction
}