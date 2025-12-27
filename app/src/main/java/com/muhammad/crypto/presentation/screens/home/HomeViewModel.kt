package com.muhammad.crypto.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.crypto.domain.model.coin.CoinUI
import com.muhammad.crypto.domain.model.coin.toCoinUi
import com.muhammad.crypto.domain.model.coin.toFavouriteCoinEntity
import com.muhammad.crypto.domain.repository.CoinRepository
import com.muhammad.crypto.domain.repository.FavouriteCryptoRepository
import com.muhammad.crypto.domain.utils.onError
import com.muhammad.crypto.domain.utils.onSuccess
import com.muhammad.crypto.utils.SnackbarEvent
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val coinRepository: CoinRepository,
    private val favouriteCryptoRepository: FavouriteCryptoRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = combine(
        _state, favouriteCryptoRepository.getAllFavouriteIds()
    ) { state, favouriteIds ->
        state.copy(favouriteCryptoId = favouriteIds, coins = state.coins.map { coin ->
            coin.copy(isFavourite = coin.id in favouriteIds)
        })
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), HomeState())
    private val _snackbarEvent = Channel<SnackbarEvent>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()
    private var refreshJob: Job? = null

    init {
        onAction(HomeAction.RefreshCoins)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.GetCoinsData -> getCoinsData()
            is HomeAction.OnSearchChange -> {
                _state.update { current ->
                    val filtered = if (action.text.isEmpty()) {
                        current.allCoins
                    } else {
                        current.allCoins.filter { coin ->
                            coin.name.contains(
                                action.text,
                                ignoreCase = true
                            ) || coin.symbol.contains(action.text, ignoreCase = true)
                        }
                    }
                    current.copy(searchQuery = action.text, coins = filtered)
                }
            }

            HomeAction.RefreshCoins -> refreshCoins()
            HomeAction.GetFavouriteCoinsIds -> getFavouriteCoinsIds()
            is HomeAction.OnCryptoFavouriteToggle -> onCryptoFavouriteToggle(action.coinUI)
        }
    }

    private fun onCryptoFavouriteToggle(coin: CoinUI) {
        viewModelScope.launch {
            if (coin.isFavourite) {
                favouriteCryptoRepository.deleteFavouriteCrypto(coin.id)
                delay(60)
                _snackbarEvent.send(SnackbarEvent.ShowSnackbar(message = "Favourite Coin deleted!"))
            } else {
                favouriteCryptoRepository.insertFavouriteCrypto(coin.toFavouriteCoinEntity())
                _snackbarEvent.send(SnackbarEvent.ShowSnackbar(message = "Favourite Coin added!"))
            }
        }
    }

    private fun getFavouriteCoinsIds() {
        favouriteCryptoRepository.getAllFavouriteIds().onEach { ids ->
            _state.update { it.copy(favouriteCryptoId = ids) }
        }.launchIn(viewModelScope)
    }

    private fun refreshCoins() {
        if (refreshJob == null) {
            refreshJob = viewModelScope.launch {
                while (true) {
                    onAction(HomeAction.GetCoinsData)
                    delay(60_000L)
                }
            }
        }
    }

    private fun getCoinsData() {
        viewModelScope.launch {
            _state.update {
                if (state.value.coins.isEmpty()) {
                    it.copy(isCoinsLoading = true)
                } else {
                    it.copy(isRefreshing = true)
                }
            }
            coinRepository.getCoins().onSuccess { data ->
                val mapped = data.map { coin ->
                    coin.toCoinUi().copy(isFavourite = coin.id in state.value.favouriteCryptoId)
                }
                _state.update {
                    if (state.value.coins.isEmpty()) {
                        it.copy(
                            isCoinsLoading = false,
                            coinError = null,
                            coins = mapped,
                            allCoins = mapped
                        )
                    } else {
                        it.copy(
                            isRefreshing = false,
                            refreshingError = null,
                            coins = mapped
                        )
                    }
                }
            }.onError { error ->
                _state.update {
                    if (state.value.coins.isEmpty()) {
                        it.copy(
                            isCoinsLoading = false,
                            coinError = error
                        )
                    } else {
                        it.copy(
                            isRefreshing = false,
                            refreshingError = error
                        )
                    }
                }
                if (state.value.coins.isNotEmpty()) {
                    delay(60)
                    _snackbarEvent.send(
                        SnackbarEvent.ShowSnackbar(
                            message = "Failed to refresh coins!"
                        )
                    )
                }
            }
        }
    }
}