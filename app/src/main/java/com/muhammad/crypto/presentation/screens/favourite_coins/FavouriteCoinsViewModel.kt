package com.muhammad.crypto.presentation.screens.favourite_coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhammad.crypto.domain.repository.FavouriteCryptoRepository
import com.muhammad.crypto.utils.SnackbarEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavouriteCoinsViewModel(
    private val favouriteCryptoRepository: FavouriteCryptoRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(FavouriteCoinState())
    private val _snackbarEvent = Channel<SnackbarEvent>()
    val snackbarEvent = _snackbarEvent.receiveAsFlow()
    val state = combine(
        _state,
        favouriteCryptoRepository.getAllFavouriteCryptos()
    ) { state, favouriteCoins ->
        state.copy(favouriteCoins = favouriteCoins)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), FavouriteCoinState())

    fun onAction(action: FavouriteCoinsAction) {
        when (action) {
            is FavouriteCoinsAction.OnDeleteFavouriteCoin -> onDeleteFavouriteCoin(action.id)
        }
    }

    private fun onDeleteFavouriteCoin(id: String) {
        viewModelScope.launch {
            favouriteCryptoRepository.deleteFavouriteCrypto(id)
            delay(60)
            _snackbarEvent.send(SnackbarEvent.ShowSnackbar(message = "Favourite Coin deleted!"))
        }
    }
}