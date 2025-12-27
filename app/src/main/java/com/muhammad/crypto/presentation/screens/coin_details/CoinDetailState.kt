package com.muhammad.crypto.presentation.screens.coin_details

import com.muhammad.crypto.domain.model.coin.CoinUI

data class CoinDetailState(
    val coinDetail : CoinUI?=null,
    val isDetailLoading : Boolean = false,
    val detailError : String?=null
)