package com.muhammad.crypto.data.local.entity

import androidx.room.*

@Entity
data class FavouriteCryptoEntity(
    @PrimaryKey
    val id : String,
    val rank: Int,
    val name: String,
    val symbol: String
)