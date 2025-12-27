package com.muhammad.crypto.data.remote.mappers

import android.annotation.SuppressLint
import com.muhammad.crypto.data.remote.dto.CoinDto
import com.muhammad.crypto.data.remote.dto.CoinPriceDto
import com.muhammad.crypto.domain.model.coin.Coin
import com.muhammad.crypto.domain.model.coin.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        symbol = symbol,
        name = name,
        marketCapUsd = marketCapUsd,
        volumeUsd24Hr = volumeUsd24Hr,
        vwap24Hr = volumeUsd24Hr,
        maxSupply = maxSupply,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr
    )
}

@SuppressLint("NewApi")
fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        datetime = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault())
    )
}