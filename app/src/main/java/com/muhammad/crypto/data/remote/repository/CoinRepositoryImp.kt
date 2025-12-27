package com.muhammad.crypto.data.remote.repository

import com.muhammad.crypto.data.remote.network.CoinDataSource
import com.muhammad.crypto.domain.model.coin.Coin
import com.muhammad.crypto.domain.model.coin.CoinPrice
import com.muhammad.crypto.domain.repository.CoinRepository
import com.muhammad.crypto.domain.utils.Result
import java.time.ZonedDateTime

class CoinRepositoryImp(
    private val coinDataSource: CoinDataSource
) : CoinRepository{
    override suspend fun getCoins(): Result<List<Coin>> {
        return coinDataSource.getCoins()
    }

    override suspend fun getSingleCoin(coinId: String): Result<Coin> {
        return coinDataSource.getSingleCoin(coinId)
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime,
    ): Result<List<CoinPrice>> {
        return coinDataSource.getCoinHistory(coinId = coinId, start = start, end = end)
    }
}