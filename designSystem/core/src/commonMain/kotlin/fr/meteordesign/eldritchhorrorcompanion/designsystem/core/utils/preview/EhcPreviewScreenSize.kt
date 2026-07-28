package fr.meteordesign.eldritchhorrorcompanion.designsystem.core.utils.preview

import androidx.compose.ui.tooling.preview.Devices.FOLDABLE
import androidx.compose.ui.tooling.preview.Devices.PHONE
import androidx.compose.ui.tooling.preview.Devices.TABLET
import androidx.compose.ui.tooling.preview.Preview

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Preview(
    name = "31 Screen Size - Smartphone portrait",
    group = "ScreenSize",
    device = "$PHONE,orientation=portrait",
)
@Preview(
    name = "32 Screen Size - Smartphone landscape",
    group = "ScreenSize",
    device = "$PHONE,orientation=landscape",
)
@Preview(
    name = "33 Screen Size - Tablet portrait",
    group = "ScreenSize",
    device = "$TABLET,orientation=portrait",
)
@Preview(
    name = "34 Screen Size - Tablet landscape",
    group = "ScreenSize",
    device = "$TABLET,orientation=landscape",
)
@Preview(
    name = "35 Screen Size - Foldable portrait",
    group = "ScreenSize",
    device = "$FOLDABLE,orientation=portrait",
)
@Preview(
    name = "36 Screen Size - Foldable landscape",
    group = "ScreenSize",
    device = "$FOLDABLE,orientation=landscape",
)
internal annotation class EhcPreviewScreenSize
