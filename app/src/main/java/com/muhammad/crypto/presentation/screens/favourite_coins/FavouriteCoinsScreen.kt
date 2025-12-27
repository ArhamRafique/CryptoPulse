package com.muhammad.crypto.presentation.screens.favourite_coins

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.muhammad.crypto.R
import com.muhammad.crypto.domain.utils.ObserveAsEvents
import com.muhammad.crypto.presentation.navigation.Destinations
import com.muhammad.crypto.presentation.screens.favourite_coins.components.FavouriteCoinCard
import com.muhammad.crypto.utils.SnackbarEvent
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FavouriteCoinsScreen(
    navHostController: NavHostController,
    viewModel: FavouriteCoinsViewModel = koinViewModel(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(viewModel.snackbarEvent, collectLatest = true) {event ->
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
                Text(text = stringResource(R.string.favourite_coins))
            },
            navigationIcon = {
                IconButton(onClick = {
                    navHostController.navigateUp()
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                        contentDescription = null
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
    }) { paddingValues ->
        AnimatedContent(
            state.favouriteCoins.isNotEmpty(),
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            modifier = Modifier
                .fillMaxSize()
        ) { showFavourites ->
            if (showFavourites) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    contentPadding = paddingValues,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.favouriteCoins, key = { it.id }) { favouriteCoin ->
                        FavouriteCoinCard(favouriteCoin = favouriteCoin, onCoinUnFavourite = { id ->
                            viewModel.onAction(FavouriteCoinsAction.OnDeleteFavouriteCoin(id))
                        }, onCoinDetails = { id ->
                            navHostController.navigate(Destinations.CoinDetailsScreen(id))
                        })
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        16.dp,
                        Alignment.CenterVertically
                    )
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_favourite_filled),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = stringResource(R.string.no_favourite_coins_found),
                        style = MaterialTheme.typography.bodyMedium.copy(textAlign = TextAlign.Center)
                    )
                }
            }
        }
    }
}