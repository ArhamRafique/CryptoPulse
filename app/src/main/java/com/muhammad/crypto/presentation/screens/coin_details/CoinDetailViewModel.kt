package com.muhammad.crypto.presentation.screens.coin_details

import android.annotation.SuppressLint
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.muhammad.crypto.data.remote.network.CoinDataSource
import com.muhammad.crypto.domain.utils.onError
import com.muhammad.crypto.domain.utils.onSuccess
import com.muhammad.crypto.presentation.navigation.Destinations
import com.muhammad.crypto.domain.model.coin.toCoinUi
import com.muhammad.crypto.domain.model.coin_detail.DataPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinDetailViewModel(
    saveStateHandle: SavedStateHandle,
    private val coinDataSource: CoinDataSource,
) : ViewModel() {
    private val coinId = saveStateHandle.toRoute<Destinations.CoinDetailsScreen>().id
    private val _state = MutableStateFlow(CoinDetailState())
    val state = _state.asStateFlow()
    init {
        onAction(CoinDetailAction.GetCoinDetails)
    }

    fun onAction(action: CoinDetailAction) {
        when (action) {
            CoinDetailAction.GetCoinDetails -> getCoinDetails(coinId)
        }
    }

    @SuppressLint("NewApi")
    private fun getCoinDetails(coinId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isDetailLoading = true) }
            coinDataSource.getSingleCoin(coinId).onSuccess { coin ->
                coinDataSource.getCoinHistory(
                    coinId = coinId,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                ).onSuccess { history ->
                    val dataPoints = history.sortedBy { it.datetime }.map { price ->
                        DataPoint(
                            x = price.datetime.hour.toFloat(),
                            y = price.priceUsd.toFloat(),
                            xLabel = DateTimeFormatter.ofPattern("ha\nM/d").format(price.datetime)
                        )
                    }
                    _state.update {
                        it.copy(
                            isDetailLoading = false,
                            detailError = null,
                            coinDetail = coin.toCoinUi().copy(coinPriceHistory = dataPoints)
                        )
                    }
                }.onError {error ->
                    _state.update {
                        it.copy(
                            isDetailLoading = false,
                            detailError = error
                        )
                    }
                }
            }.onError { error ->
                _state.update {
                    it.copy(
                        isDetailLoading = false,
                        detailError = error
                    )
                }
            }
        }
    }
}