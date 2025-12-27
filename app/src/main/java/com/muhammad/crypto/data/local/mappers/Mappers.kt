package com.muhammad.crypto.data.local.mappers

import com.muhammad.crypto.data.local.entity.FavouriteCryptoEntity
import com.muhammad.crypto.domain.model.favourite.FavouriteCoin
import com.muhammad.crypto.utils.getDrawableIdForCoin

fun FavouriteCryptoEntity.toFavouriteCoin(): FavouriteCoin {
    return FavouriteCoin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol, iconRes = getDrawableIdForCoin(symbol)
    )
}

fun FavouriteCoin.toFavouriteCryptoEntity(): FavouriteCryptoEntity {
    return FavouriteCryptoEntity(
        id = id,
        rank = rank,
        symbol = symbol,
        name = name
    )
}

