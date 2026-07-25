package fr.meteordesign.eldritchhorrorcompanion.designsystem.core.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.utils.ehcFillMaxSize

@Composable
fun EhcScaffold(
    modifier: Modifier = Modifier,
    topBar: (@Composable () -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    // Prevent creation of a new lamba at each recomposition
    val emptyContent = remember<@Composable () -> Unit> { {} }

    Scaffold(
        modifier = modifier
            .ehcFillMaxSize(),
        topBar = topBar ?: emptyContent,
        content = content,
    )
}
