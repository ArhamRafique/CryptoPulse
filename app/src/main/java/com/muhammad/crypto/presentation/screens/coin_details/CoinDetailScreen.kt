package com.muhammad.crypto.presentation.screens.coin_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.crypto.R
import com.muhammad.crypto.domain.model.coin.toDisplayableNumber
import com.muhammad.crypto.domain.model.coin_detail.ChartStyle
import com.muhammad.crypto.domain.model.coin_detail.DataPoint
import com.muhammad.crypto.presentation.screens.coin_details.components.CoinInfoCard
import com.muhammad.crypto.presentation.screens.coin_details.components.LineChart
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CoinDetailScreen(
    navHostController: NavHostController,
    viewModel: CoinDetailViewModel = koinViewModel()
) {
    var selectedDataPoint by remember {
        mutableStateOf<DataPoint?>(null)
    }
    var labelWidth by remember {
        mutableFloatStateOf(0f)
    }
    var totalChartWidth by remember {
        mutableFloatStateOf(0f)
    }
    val amountOfVisibleDataPoints = if(labelWidth > 0){
        ((totalChartWidth - 2.5 * labelWidth) / labelWidth).toInt()
    } else 0
    val state by viewModel.state.collectAsStateWithLifecycle()
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        when {
            state.isDetailLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    ContainedLoadingIndicator()
                }
            }
            state.detailError != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_error),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = state.detailError!!,
                        style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
                    )
                }
            }
            else -> {
                state.coinDetail?.let { coin ->
                    val startIndex = (coin.coinPriceHistory.lastIndex - amountOfVisibleDataPoints)
                        .coerceAtLeast(0)
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(coin.iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = coin.name,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Black,
                                textAlign = TextAlign.Center
                            )
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = coin.symbol,
                            style = MaterialTheme.typography.titleLarge.copy(textAlign = TextAlign.Center)
                        )
                        Spacer(Modifier.height(24.dp))
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                16.dp,
                                Alignment.CenterHorizontally
                            ), verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CoinInfoCard(
                                label = R.string.market_cap,
                                value = "$ ${coin.markCapUsd.formatted}",
                                icon = R.drawable.ic_market_cap
                            )
                            CoinInfoCard(
                                label = R.string.price,
                                value = "$ ${coin.priceUsd.formatted}",
                                icon = R.drawable.ic_price
                            )
                            CoinInfoCard(
                                label = R.string.change_last_24h,
                                value = (coin.priceUsd.value * (coin.changePercent24Hr.value / 100)).toDisplayableNumber().formatted,
                                icon = if (coin.changePercent24Hr.value > 0.0) R.drawable.ic_trending else R.drawable.ic_trending_down
                            )
                            CoinInfoCard(
                                label = R.string.volume_price_last_24h,
                                value = coin.volumeUsd24Hr.formatted,
                                icon = R.drawable.ic_volume_price
                            )
                            CoinInfoCard(
                                label = R.string.vwap,
                                value = coin.vwap24Hr.formatted,
                                icon = R.drawable.ic_volume_price
                            )
                            coin.maxSupply?.let {
                                CoinInfoCard(
                                    label = R.string.max_supply,
                                    value = it.formatted,
                                    icon = R.drawable.ic_supply
                                )
                            }
                        }
                        Spacer(Modifier.height(24.dp))
                        LineChart(
                            dataPoints = coin.coinPriceHistory,
                            style = ChartStyle(
                                chartLineColor = MaterialTheme.colorScheme.primary,
                                unSelectedColor = MaterialTheme.colorScheme.secondary.copy(
                                    alpha = 0.3f
                                ),
                                selectedColor = MaterialTheme.colorScheme.primary,
                                helperLineThicknessPx = 5f,
                                axisLinesThicknessPx = 5f,
                                labelFontSize = 14.sp,
                                minYLabelSpacing = 25.dp,
                                verticalPadding = 8.dp,
                                horizontalPadding = 8.dp,
                                xAxisLabelSpacing = 8.dp
                            ),
                            visibleDataPointsIndices = startIndex..coin.coinPriceHistory.lastIndex,
                            unit = "$",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16 / 9f)
                                .onSizeChanged { totalChartWidth = it.width.toFloat() },
                            selectedDataPoint = selectedDataPoint,
                            onSelectedDataPoint = {
                                selectedDataPoint = it
                            },
                            onXLabelWidthChange = { labelWidth = it }
                        )
                    }
                }
            }
        }
    }
}