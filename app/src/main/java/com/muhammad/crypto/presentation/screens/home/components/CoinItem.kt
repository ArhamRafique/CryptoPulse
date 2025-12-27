package com.muhammad.crypto.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.crypto.R
import com.muhammad.crypto.domain.model.coin.CoinUI

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CoinItem(
    modifier: Modifier = Modifier,
    coinUI: CoinUI,
    onCoinClick: (String) -> Unit,
    onFavoriteToggle: (CoinUI) -> Unit,
) {
    Card(
        modifier = modifier,
        onClick = {
            onCoinClick(coinUI.id)
        },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier.size(85.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(coinUI.iconRes),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = MaterialTheme.colorScheme.primary
                )
                IconButton(
                    onClick = {
                        onFavoriteToggle(coinUI)
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .size(IconButtonDefaults.extraSmallContainerSize())
                        .offset(x = (-8).dp, y = 8.dp)
                ) {
                    val icon =
                        if (coinUI.isFavourite) R.drawable.ic_favourite_filled else R.drawable.ic_favourite_outlined
                    Icon(
                        imageVector = ImageVector.vectorResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(IconButtonDefaults.extraSmallIconSize)
                    )
                }
                Row(
                    modifier = Modifier
                        .offset(x = (-8).dp, y = (-8).dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_rank),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = coinUI.rank.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = coinUI.symbol,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = coinUI.name,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "$ ${coinUI.priceUsd.formatted}",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                PriceChange(change = coinUI.changePercent24Hr)
            }
        }
    }
}