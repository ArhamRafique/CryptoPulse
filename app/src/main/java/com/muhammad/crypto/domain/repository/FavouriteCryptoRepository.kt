package com.muhammad.crypto.domain.repository

import com.muhammad.crypto.data.local.entity.FavouriteCryptoEntity
import com.muhammad.crypto.domain.model.favourite.FavouriteCoin
import kotlinx.coroutines.flow.Flow

interface FavouriteCryptoRepository {
    fun getAllFavouriteCryptos(): Flow<List<FavouriteCoin>>
    suspend fun insertFavouriteCrypto(favouriteCryptoEntity: FavouriteCryptoEntity)
    suspend fun deleteFavouriteCrypto(id: String)
    fun getAllFavouriteIds(): Flow<List<String>>
}