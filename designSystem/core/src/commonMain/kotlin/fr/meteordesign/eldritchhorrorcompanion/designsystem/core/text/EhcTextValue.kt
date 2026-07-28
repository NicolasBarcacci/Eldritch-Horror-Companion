package fr.meteordesign.eldritchhorrorcompanion.designsystem.core.text

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed interface EhcTextValue {

    @Composable
    fun getString(): kotlin.String

    data class String(val value: kotlin.String) : EhcTextValue {
        @Composable
        override fun getString(): kotlin.String = value
    }

    data class StringRes(val value: StringResource) : EhcTextValue {
        @Composable
        override fun getString(): kotlin.String = stringResource(value)
    }
}
