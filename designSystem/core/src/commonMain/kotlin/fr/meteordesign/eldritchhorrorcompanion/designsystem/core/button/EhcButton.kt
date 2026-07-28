package fr.meteordesign.eldritchhorrorcompanion.designsystem.core.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.color.EldritchArcanePurple
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.color.EldritchBrightInk
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.color.EldritchDisabledBackground
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.color.EldritchMutedInk
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.text.EhcText
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.text.EhcTextStyle
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.text.EhcTextValue
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.theme.EhcTheme

private val EhcButtonPrimaryShape = RoundedCornerShape(50)

@Composable
fun EhcButtonPrimary(
    modifier: Modifier = Modifier,
    text: EhcTextValue,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = when {
            enabled -> modifier.shadow(
                elevation = 12.dp,
                shape = EhcButtonPrimaryShape,
                ambientColor = EldritchArcanePurple,
                spotColor = EldritchArcanePurple,
            )

            else -> modifier
        },
        enabled = enabled,
        shape = EhcButtonPrimaryShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = EldritchArcanePurple,
            contentColor = EldritchBrightInk,
            disabledContainerColor = EldritchDisabledBackground,
            disabledContentColor = EldritchMutedInk,
        ),
        contentPadding = PaddingValues(vertical = 16.dp, horizontal = 24.dp),
    ) {
        EhcText(
            text = text,
            textStyle = EhcTextStyle.Cta,
        )
    }
}

@Composable
fun EhcButtonSecondary(
    modifier: Modifier = Modifier,
    text: EhcTextValue,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        EhcText(
            text = text,
            textStyle = EhcTextStyle.Cta,
        )
    }
}

@Preview
@Composable
private fun EhcButtonPreview() {
    EhcTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            EhcButtonPrimary(
                text = EhcTextValue.String("Primary"),
                onClick = {},
            )
            EhcButtonPrimary(
                text = EhcTextValue.String("Primary disabled"),
                enabled = false,
                onClick = {},
            )
            EhcButtonSecondary(
                text = EhcTextValue.String("Secondary"),
                onClick = {},
            )
            EhcButtonSecondary(
                text = EhcTextValue.String("Secondary disabled"),
                enabled = false,
                onClick = {},
            )
        }
    }
}
