package fr.meteordesign.eldritchhorrorcompanion.designsystem.core.utils.preview

import androidx.compose.ui.tooling.preview.Preview

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Preview(
    name = "21 Font Scale - Small",
    group = "FontScale",
    fontScale = 0.5f,
)
@Preview(
    name = "22 Font Scale - Large",
    group = "FontScale",
    fontScale = 1.5f,
)
internal annotation class EhcPreviewFontScale
