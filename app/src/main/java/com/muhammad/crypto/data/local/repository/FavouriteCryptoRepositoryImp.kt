package com.muhammad.crypto.data.local.repository

import com.muhammad.crypto.data.local.dao.FavouriteCryptoDao
import com.muhammad.crypto.data.local.entity.FavouriteCryptoEntity
import com.muhammad.crypto.data.local.mappers.toFavouriteCoin
import com.muhammad.crypto.domain.model.favourite.FavouriteCoin
import com.muhammad.crypto.domain.repository.FavouriteCryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouriteCryptoRepositoryImp(
    private val favouriteCryptoDao: FavouriteCryptoDao,
) : FavouriteCryptoRepository {
    override fun getAllFavouriteCryptos(): Flow<List<FavouriteCoin>> {
        return favouriteCryptoDao.getAllFavouriteCryptos()
            .map { coinEntity -> coinEntity.map { coin -> coin.toFavouriteCoin() } }
    }

    override suspend fun insertFavouriteCrypto(favouriteCryptoEntity: FavouriteCryptoEntity) {
        favouriteCryptoDao.insertFavouriteCrypto(favouriteCryptoEntity)
    }

    override suspend fun deleteFavouriteCrypto(id: String) {
        favouriteCryptoDao.deleteFavouriteCrypto(id)
    }

    override fun getAllFavouriteIds(): Flow<List<String>> {
        return favouriteCryptoDao.getAllFavouriteIds()
    }
}