package com.muhammad.crypto.presentation.screens.coin_details.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CoinInfoCard(
    modifier: Modifier = Modifier,
    icon: Int,
    @StringRes label: Int,
    value: String,
) {
    Card(
        modifier = modifier
            .dropShadow(
                shape = RoundedCornerShape(16.dp), shadow = Shadow(
                    radius = 4.dp, spread = 2.dp, color = MaterialTheme.colorScheme.primary
                )
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                modifier = Modifier.size(75.dp)
            )
            Text(text = value, style = MaterialTheme.typography.bodyLarge)
            Text(
                text = stringResource(label), style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 12.sp, fontWeight = FontWeight.Light
                )
            )
        }
    }
}