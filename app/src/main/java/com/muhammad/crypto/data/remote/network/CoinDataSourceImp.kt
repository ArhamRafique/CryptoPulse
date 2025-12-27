package com.muhammad.crypto.data.remote.network

import android.annotation.SuppressLint
import com.muhammad.crypto.data.remote.dto.CoinHistoryDto
import com.muhammad.crypto.data.remote.dto.CoinResponseDto
import com.muhammad.crypto.data.remote.dto.SingleCoinResponseDto
import com.muhammad.crypto.data.remote.mappers.toCoin
import com.muhammad.crypto.data.remote.mappers.toCoinPrice
import com.muhammad.crypto.data.remote.network.client.get
import com.muhammad.crypto.domain.model.coin.Coin
import com.muhammad.crypto.domain.model.coin.CoinPrice
import com.muhammad.crypto.domain.utils.Result
import com.muhammad.crypto.domain.utils.map
import io.ktor.client.HttpClient
import java.time.ZoneId
import java.time.ZonedDateTime

class CoinDataSourceImp(
    private val httpClient : HttpClient
) : CoinDataSource{
    override suspend fun getCoins(): Result<List<Coin>> {
        return httpClient.get<CoinResponseDto>("assets").map {
            it.data.map {coin -> coin.toCoin() }
        }
    }

    override suspend fun getSingleCoin(coinId: String): Result<Coin> {
        return httpClient.get<SingleCoinResponseDto>("assets/$coinId").map { response ->
            response.data.toCoin()
        }
    }

    @SuppressLint("NewApi")
    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime,
    ): Result<List<CoinPrice>> {
        val startMillis = start.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        val endMillis = end.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        return httpClient.get<CoinHistoryDto>(route = "assets/$coinId/history", queryParameters = mapOf(
            "interval" to "h6",
            "start" to startMillis,
            "end" to endMillis
        )).map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}