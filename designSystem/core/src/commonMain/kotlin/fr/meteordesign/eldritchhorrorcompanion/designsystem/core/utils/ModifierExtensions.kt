package fr.meteordesign.eldritchhorrorcompanion.designsystem.core.utils

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier

fun Modifier.ehcFillMaxWidth(): Modifier =
    this
        .fillMaxWidth()

fun Modifier.ehcFillMaxHeigh(): Modifier =
    this
        .fillMaxHeight()

fun Modifier.ehcFillMaxSize(): Modifier =
    this
        .ehcFillMaxWidth()
        .ehcFillMaxHeigh()
