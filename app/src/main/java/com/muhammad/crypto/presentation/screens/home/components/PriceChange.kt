package com.muhammad.crypto.presentation.screens.home.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.muhammad.crypto.R
import com.muhammad.crypto.domain.model.coin.DisplayableNumber
import com.muhammad.crypto.presentation.theme.greenBackground

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PriceChange(change: DisplayableNumber, modifier: Modifier = Modifier) {
    val containerColor by animateColorAsState(
        targetValue = if (change.value < 0.0) MaterialTheme.colorScheme.error else greenBackground,
        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
        label = "containerColor"
    )
    val contentColor by animateColorAsState(
        targetValue = if (change.value < 0.0) MaterialTheme.colorScheme.onError else Color.White,
        animationSpec = MaterialTheme.motionScheme.fastEffectsSpec(),
        label = "containerColor"
    )
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(containerColor)
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(
                if (change.value < 0.0) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
            ), contentDescription = null, modifier = Modifier.size(20.dp), tint = contentColor
        )
        Text(
            text = "${change.formatted} %",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
        )
    }
}