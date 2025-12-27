package com.muhammad.crypto.presentation.screens.home

import com.muhammad.crypto.domain.model.coin.CoinUI

sealed interface HomeAction{
    data object GetCoinsData : HomeAction
    data class OnSearchChange(val text : String) : HomeAction
    data object RefreshCoins : HomeAction
    data object GetFavouriteCoinsIds : HomeAction
    data class OnCryptoFavouriteToggle(val coinUI : CoinUI) : HomeAction
}