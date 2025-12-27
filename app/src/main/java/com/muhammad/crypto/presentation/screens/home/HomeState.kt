package com.muhammad.crypto.presentation.screens.home

import com.muhammad.crypto.domain.model.coin.CoinUI

data class HomeState(
    val allCoins : List<CoinUI> = emptyList(),
    val coins : List<CoinUI> = emptyList(),
    val favouriteCryptoId : List<String> = emptyList(),
    val searchQuery : String = "",
    val isRefreshing : Boolean = false,
    val refreshingError : String?=null,
    val isCoinsLoading : Boolean = false,
    val coinError : String?=null
)