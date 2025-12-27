package com.muhammad.crypto.presentation.screens.favourite_coins.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.muhammad.crypto.domain.model.favourite.FavouriteCoin

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FavouriteCoinCard(
    modifier: Modifier = Modifier,
    favouriteCoin: FavouriteCoin,
    onCoinDetails : (String) -> Unit,
    onCoinUnFavourite: (String) -> Unit,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        onClick = {
            onCoinDetails(favouriteCoin.id)
        },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomEnd = 24.dp,
                            bottomStart = 24.dp
                        )
                    )
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(favouriteCoin.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .offset(x = (-12).dp, y = (-12).dp)
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
                        text = favouriteCoin.rank.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = favouriteCoin.symbol,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = favouriteCoin.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                IconButton(
                    onClick = {
                        onCoinUnFavourite(favouriteCoin.id)
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .size(IconButtonDefaults.extraSmallContainerSize())
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_unlike),
                        contentDescription = null,
                        modifier = Modifier.size(IconButtonDefaults.extraSmallIconSize)
                    )
                }
            }
        }
    }
}