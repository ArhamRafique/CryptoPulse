package com.muhammad.crypto.di

import androidx.room.Room
import com.muhammad.crypto.CryptoApplication
import com.muhammad.crypto.data.local.CryptoDatabase
import com.muhammad.crypto.data.local.repository.FavouriteCryptoRepositoryImp
import com.muhammad.crypto.data.remote.network.CoinDataSource
import com.muhammad.crypto.data.remote.network.CoinDataSourceImp
import com.muhammad.crypto.data.remote.network.client.HttpClientFactory
import com.muhammad.crypto.data.remote.repository.CoinRepositoryImp
import com.muhammad.crypto.domain.repository.CoinRepository
import com.muhammad.crypto.domain.repository.FavouriteCryptoRepository
import com.muhammad.crypto.presentation.screens.coin_details.CoinDetailViewModel
import com.muhammad.crypto.presentation.screens.favourite_coins.FavouriteCoinsViewModel
import com.muhammad.crypto.presentation.screens.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { CryptoApplication.INSTANCE }
    single {
        Room.databaseBuilder(get(), CryptoDatabase::class.java, "crypto.db")
            .fallbackToDestructiveMigration(true).setQueryCoroutineContext(
            Dispatchers.IO
        ).build()
    }
    single { get<CryptoDatabase>().favouriteCryptoDao() }
    single { HttpClientFactory.createClient() }
    single { CoinDataSourceImp(get()) }.bind<CoinDataSource>()
    single { CoinRepositoryImp(get()) }.bind<CoinRepository>()
    single { FavouriteCryptoRepositoryImp(get()) }.bind<FavouriteCryptoRepository>()
    viewModelOf(::HomeViewModel)
    viewModelOf(::CoinDetailViewModel)
    viewModelOf(::FavouriteCoinsViewModel)
}