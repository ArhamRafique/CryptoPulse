package com.muhammad.crypto.domain.model.coin

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import com.muhammad.crypto.data.local.entity.FavouriteCryptoEntity
import com.muhammad.crypto.domain.model.coin_detail.DataPoint
import com.muhammad.crypto.utils.getDrawableIdForCoin
import java.text.NumberFormat
import java.util.Locale

@Immutable
data class CoinUI(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val isFavourite: Boolean,
    val markCapUsd: DisplayableNumber,
    val priceUsd: DisplayableNumber,
    val maxSupply: DisplayableNumber? = null,
    val volumeUsd24Hr: DisplayableNumber,
    val vwap24Hr: DisplayableNumber,
    val changePercent24Hr: DisplayableNumber,
    @get:DrawableRes val iconRes: Int,
    val coinPriceHistory: List<DataPoint> = emptyList(),
)

fun Coin.toCoinUi(): CoinUI {
    return CoinUI(
        id = id,
        name = name,
        symbol = symbol,
        rank = rank,
        priceUsd = priceUsd.toDisplayableNumber(),
        markCapUsd = marketCapUsd.toDisplayableNumber(),
        changePercent24Hr = changePercent24Hr.toDisplayableNumber(),
        maxSupply = maxSupply?.toDisplayableNumber(),
        volumeUsd24Hr = volumeUsd24Hr.toDisplayableNumber(),
        vwap24Hr = vwap24Hr.toDisplayableNumber(),
        iconRes = getDrawableIdForCoin(symbol), isFavourite = false
    )
}

fun CoinUI.toFavouriteCoinEntity(): FavouriteCryptoEntity {
    return FavouriteCryptoEntity(id = id, name = name, rank = rank, symbol = symbol)
}


@Immutable
data class DisplayableNumber(
    val value: Double,
    val formatted: String,
)

fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return DisplayableNumber(
        value = this,
        formatted = formatter.format(this)
    )
}