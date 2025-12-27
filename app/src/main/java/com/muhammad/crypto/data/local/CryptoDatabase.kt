package com.muhammad.crypto.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muhammad.crypto.data.local.dao.FavouriteCryptoDao
import com.muhammad.crypto.data.local.entity.FavouriteCryptoEntity

@Database(
    entities = [FavouriteCryptoEntity::class],
    version = 2, exportSchema = true
)
abstract class CryptoDatabase : RoomDatabase(){
    abstract fun favouriteCryptoDao() : FavouriteCryptoDao
}