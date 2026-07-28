package fr.meteordesign.eldritchhorrorcompanion.designsystem.core.text

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

sealed class EhcTextStyle(val textStyle: TextStyle) {

    data object Cta : EhcTextStyle(
        TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.5.sp,
            letterSpacing = 0.5.sp,
        ),
    )
}
