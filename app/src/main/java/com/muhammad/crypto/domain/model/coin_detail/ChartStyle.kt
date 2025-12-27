package com.muhammad.crypto.domain.model.coin_detail

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Immutable
data class ChartStyle(
    val chartLineColor : Color,
    val unSelectedColor : Color,
    val selectedColor : Color,
    val helperLineThicknessPx : Float,
    val axisLinesThicknessPx : Float,
    val labelFontSize : TextUnit,
    val minYLabelSpacing : Dp,
    val verticalPadding : Dp,
    val horizontalPadding : Dp,
    val xAxisLabelSpacing : Dp
)