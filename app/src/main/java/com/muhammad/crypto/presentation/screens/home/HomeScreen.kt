package com.muhammad.crypto.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.crypto.R
import com.muhammad.crypto.domain.utils.ObserveAsEvents
import com.muhammad.crypto.presentation.components.AppTextField
import com.muhammad.crypto.presentation.navigation.Destinations
import com.muhammad.crypto.presentation.screens.home.components.CoinItem
import com.muhammad.crypto.utils.SnackbarEvent
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun HomeScreen(navHostController: NavHostController, viewModel: HomeViewModel = koinViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.snackbarEvent, collectLatest = true) { event ->
        when (event) {
            is SnackbarEvent.ShowSnackbar -> {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
        SnackbarHost(snackbarHostState)
    }, topBar = {
        CenterAlignedTopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_logo),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(text = stringResource(R.string.app_name))
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        navHostController.navigate(Destinations.FavouriteCoinsScreen)
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_favourite_filled),
                        contentDescription = null
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
    }) { paddingValues ->
        when {
            state.isCoinsLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    ContainedLoadingIndicator()
                }
            }

            state.coinError != null -> {
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
                        text = state.coinError!!,
                        style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
                    )
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (state.isRefreshing) {
                        stickyHeader {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .animateItem()
                                    .padding(top = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.surfaceContainer)
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(18.dp),
                                        strokeWidth = 2.dp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = stringResource(R.string.refreshing),
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    item("search_bar") {
                        AppTextField(value = state.searchQuery, onValueChange = {
                            viewModel.onAction(HomeAction.OnSearchChange(it))
                        }, hint = R.string.search_coins, leadingIcon = R.drawable.ic_search)
                    }
                    items(state.coins, key = { it.id }) { coin ->
                        CoinItem(
                            coinUI = coin,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem(),
                            onCoinClick = { id ->
                                navHostController.navigate(Destinations.CoinDetailsScreen(id))
                            }, onFavoriteToggle = { coin ->
                                viewModel.onAction(HomeAction.OnCryptoFavouriteToggle(coin))
                            })
                    }
                }
            }
        }
    }
}