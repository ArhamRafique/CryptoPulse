package com.muhammad.crypto.data.remote.network

import com.muhammad.crypto.domain.model.coin.Coin
import com.muhammad.crypto.domain.model.coin.CoinPrice
import com.muhammad.crypto.domain.utils.Result
import java.time.ZonedDateTime

interface CoinDataSource{
    suspend fun getCoins() : Result<List<Coin>>
    suspend fun getSingleCoin(coinId : String) : Result<Coin>
    suspend fun getCoinHistory(
        coinId : String,
        start : ZonedDateTime,
        end : ZonedDateTime
    ) : Result<List<CoinPrice>>
}