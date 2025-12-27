package com.muhammad.crypto.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.muhammad.crypto.data.local.entity.FavouriteCryptoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteCryptoDao {
    @Query("SELECT * FROM favouritecryptoentity")
    fun getAllFavouriteCryptos(): Flow<List<FavouriteCryptoEntity>>

    @Query("SELECT id FROM favouritecryptoentity")
    fun getAllFavouriteIds(): Flow<List<String>>

    @Insert
    suspend fun insertFavouriteCrypto(favouriteCryptoEntity: FavouriteCryptoEntity)

    @Query("DELETE FROM favouritecryptoentity WHERE id = :id")
    suspend fun deleteFavouriteCrypto(id: String)
}