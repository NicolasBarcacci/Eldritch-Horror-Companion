package fr.meteordesign.eldritchhorrorcompanion.features.testresolver.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eldritchhorrorcompanion.designsystem.core.generated.resources.ic_lock_closed
import eldritchhorrorcompanion.designsystem.core.generated.resources.ic_lock_opened
import eldritchhorrorcompanion.features.core.generated.resources.Res
import eldritchhorrorcompanion.features.core.generated.resources.status_blessed
import eldritchhorrorcompanion.features.core.generated.resources.status_cursed
import eldritchhorrorcompanion.features.core.generated.resources.status_none
import eldritchhorrorcompanion.features.core.generated.resources.test_resolver_successes_count
import eldritchhorrorcompanion.features.core.generated.resources.test_resolver_title
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.scaffold.EhcScaffold
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.theme.EhcTheme
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.utils.ehcFillMaxSize
import fr.meteordesign.eldritchhorrorcompanion.designsystem.core.utils.preview.EhcPreview
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.Configuration.Status
import fr.meteordesign.eldritchhorrorcompanion.features.testresolver.model.TestResolverUiModel.TestResult
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import eldritchhorrorcompanion.designsystem.core.generated.resources.Res as DesignSystemRes

private const val MaxDicePerRow = 5

// Eldritch dark palette, specific to this screen's design.
private val PageBackground = Color(0xFF14131C)
private val GlowPurple = Color(0xFF4A2E63)
private val Ink = Color(0xFFDEDCE6)
private val TitlePurple = Color(0xFFC68FE6)
private val Purple = Color(0xFF7A3FA8)
private val CursedColor = Color(0xFFE68A5A)
private val CursedSoft = Color(0xFF4A362C)
private val BlessedColor = Color(0xFF7FA6D6)
private val BlessedSoft = Color(0xFF2B3340)
private val Gold = Color(0xFFD9B85E)
private val Rust = Color(0xFF9C5230)
private val BorderPurple = Purple.copy(alpha = 0.45f)
private val Muted = Color(0xFF7C7A82)
private val TileBackground = Color(0xFF1B1A22)
private val SegmentedBackground = Color(0xFF18161F)
private val NoneActiveBackground = Color(0xFF3A3840)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestResolverContent(
    modifier: Modifier = Modifier,
    uiModel: TestResolverUiModel,
    onStatusSelected: (Status) -> Unit,
    onIncrementDiceCount: () -> Unit,
    onDecrementDiceCount: () -> Unit,
    onDieClick: (Int) -> Unit,
    onRollDiceClick: () -> Unit,
    onClearClick: () -> Unit,
) {
    EhcScaffold(modifier = modifier) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .ehcFillMaxSize()
                .background(PageBackground),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .align(Alignment.TopCenter)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(GlowPurple.copy(alpha = 0.55f), Color.Transparent),
                        ),
                    ),
            )

            EldritchTentacles(modifier = Modifier.align(Alignment.BottomStart))
            EldritchTentacles(modifier = Modifier.align(Alignment.BottomEnd), mirrored = true)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(modifier = Modifier.padding(top = 24.dp).fillMaxWidth()) {
                    Text(
                        text = stringResource(Res.string.test_resolver_title).uppercase(),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        letterSpacing = 2.sp,
                        color = TitlePurple,
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 18.dp)
                            .height(1.dp)
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color.Transparent, BorderPurple, Color.Transparent),
                                ),
                            ),
                    )

                    StatusSegmentedControl(
                        statuses = uiModel.configuration.statuses,
                        selectedStatus = uiModel.configuration.selectedStatus,
                        onStatusSelected = onStatusSelected,
                    )
                }

                Row(
                    modifier = Modifier.padding(top = 28.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(18.dp),
                ) {
                    StepperButton(
                        text = "–",
                        enabled = uiModel.configuration.diceCount > 1,
                        onClick = onDecrementDiceCount,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = "${uiModel.configuration.diceCount}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            color = Ink,
                        )
                        DieIcon()
                    }
                    StepperButton(
                        text = "+",
                        enabled = true,
                        onClick = onIncrementDiceCount,
                    )
                }

                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 18.dp),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        ClearButton(enabled = uiModel.clearEnabled, onClick = onClearClick)
                    }

                    val result = uiModel.testResult

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        maxItemsInEachRow = MaxDicePerRow,
                    ) {
                        when (result) {
                            null -> repeat(uiModel.configuration.diceCount) {
                                DieFace(text = "?", success = false)
                            }

                            else -> result.dice.forEachIndexed { index, die ->
                                DieFace(
                                    text = "${die.roll}",
                                    success = isSuccess(die.roll, uiModel.configuration.selectedStatus),
                                    locked = !die.selected,
                                    onClick = { onDieClick(index) },
                                )
                            }
                        }
                    }

                    Text(
                        modifier = Modifier.padding(top = 18.dp),
                        text = when (result) {
                            null -> ""
                            else -> stringResource(Res.string.test_resolver_successes_count, result.successCount)
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = when {
                            result != null && result.successCount > 0 -> Gold
                            else -> Muted
                        },
                    )
                }

                RollButton(
                    label = stringResource(uiModel.rollLabel),
                    enabled = uiModel.rollEnabled,
                    onClick = onRollDiceClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 28.dp),
                )
            }
        }
    }
}

private fun isSuccess(roll: Int, status: Status): Boolean = when (status) {
    Status.Cursed -> roll == 6
    Status.Blessed -> roll >= 4
    Status.None -> roll >= 5
}

@Composable
private fun StatusSegmentedControl(
    statuses: List<Status>,
    selectedStatus: Status,
    onStatusSelected: (Status) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50))
            .border(1.dp, BorderPurple, RoundedCornerShape(50))
            .background(SegmentedBackground),
    ) {
        statuses.forEach { status ->
            val selected = status == selectedStatus
            val (activeBackground, activeText) = when (status) {
                Status.Cursed -> CursedSoft to CursedColor
                Status.Blessed -> BlessedSoft to BlessedColor
                Status.None -> NoneActiveBackground to Ink
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onStatusSelected(status) }
                    .background(if (selected) activeBackground else Color.Transparent)
                    .padding(vertical = 11.dp, horizontal = 6.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(
                        when (status) {
                            Status.Blessed -> Res.string.status_blessed
                            Status.Cursed -> Res.string.status_cursed
                            Status.None -> Res.string.status_none
                        },
                    ),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.5.sp,
                    color = if (selected) activeText else Ink,
                )
            }
        }
    }
}

@Composable
private fun StepperButton(text: String, enabled: Boolean, onClick: () -> Unit) {
    val color = if (enabled) Purple else Muted
    val border = if (enabled) BorderPurple else Muted.copy(alpha = 0.4f)

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(TileBackground)
            .border(1.dp, border, CircleShape)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = color)
    }
}

@Composable
private fun ClearButton(enabled: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(34.dp)
            .alpha(if (enabled) 1f else 0f)
            .clip(CircleShape)
            .border(1.dp, BorderPurple, CircleShape)
            .clickable(enabled = enabled, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        TrashIcon(tint = Ink)
    }
}

@Composable
private fun RollButton(label: String, enabled: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .let { base ->
                when {
                    enabled -> base.shadow(12.dp, RoundedCornerShape(50), ambientColor = Purple, spotColor = Purple)
                    else -> base
                }
            }
            .background(if (enabled) Purple else Color(0xFF262430))
            .clickable(enabled = enabled, onClick = onClick)
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 16.5.sp,
            letterSpacing = 0.5.sp,
            color = if (enabled) Color(0xFFF7F5FA) else Muted,
        )
    }
}

@Composable
private fun DieFace(
    text: String,
    success: Boolean,
    locked: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(RoundedCornerShape(11.dp))
            .let { base ->
                when {
                    success -> base.shadow(10.dp, RoundedCornerShape(11.dp), ambientColor = Gold, spotColor = Gold)
                    else -> base
                }
            }
            .background(if (success) Gold else TileBackground)
            .border(
                width = 1.5.dp,
                color = if (success) Gold else BorderPurple,
                shape = RoundedCornerShape(11.dp),
            )
            .let { base ->
                when {
                    onClick != null -> base.clickable(onClick = onClick)
                    else -> base
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = if (success) PageBackground else Ink,
        )

        if (onClick != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 0.dp)
                    .size(19.dp)
                    .clip(CircleShape)
                    .background(PageBackground)
                    .border(1.5.dp, BorderPurple, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                val lockColor = if (locked) Muted else Purple
                Box(modifier = Modifier.size(11.dp)) {
                    androidx.compose.material3.Icon(
                        painter = painterResource(
                            when {
                                locked -> DesignSystemRes.drawable.ic_lock_closed
                                else -> DesignSystemRes.drawable.ic_lock_opened
                            },
                        ),
                        contentDescription = null,
                        tint = lockColor,
                    )
                }
            }
        }
    }
}

@Composable
private fun DieIcon() {
    Canvas(modifier = Modifier.size(20.dp)) {
        val w = size.width
        val h = size.height
        fun pt(x: Float, y: Float) = Offset(x / 24f * w, y / 24f * h)

        val top = Path().apply {
            moveTo(pt(12f, 2f).x, pt(12f, 2f).y)
            lineTo(pt(21f, 7f).x, pt(21f, 7f).y)
            lineTo(pt(12f, 12f).x, pt(12f, 12f).y)
            lineTo(pt(3f, 7f).x, pt(3f, 7f).y)
            close()
        }
        val left = Path().apply {
            moveTo(pt(3f, 7f).x, pt(3f, 7f).y)
            lineTo(pt(12f, 12f).x, pt(12f, 12f).y)
            lineTo(pt(12f, 22f).x, pt(12f, 22f).y)
            lineTo(pt(3f, 17f).x, pt(3f, 17f).y)
            close()
        }
        val right = Path().apply {
            moveTo(pt(21f, 7f).x, pt(21f, 7f).y)
            lineTo(pt(12f, 12f).x, pt(12f, 12f).y)
            lineTo(pt(12f, 22f).x, pt(12f, 22f).y)
            lineTo(pt(21f, 17f).x, pt(21f, 17f).y)
            close()
        }

        drawPath(top, color = Ink)
        drawPath(left, color = Ink.copy(alpha = 0.8f))
        drawPath(right, color = Ink.copy(alpha = 0.65f))
        drawPath(top, color = PageBackground, style = Stroke(width = 0.6f))
        drawPath(left, color = PageBackground, style = Stroke(width = 0.6f))
        drawPath(right, color = PageBackground, style = Stroke(width = 0.6f))
    }
}

@Composable
private fun TrashIcon(tint: Color) {
    Canvas(modifier = Modifier.size(16.dp)) {
        val w = size.width
        val h = size.height
        fun pt(x: Float, y: Float) = Offset(x / 24f * w, y / 24f * h)

        val path = Path().apply {
            moveTo(pt(5f, 7f).x, pt(5f, 7f).y)
            lineTo(pt(19f, 7f).x, pt(19f, 7f).y)

            moveTo(pt(10f, 7f).x, pt(10f, 7f).y)
            lineTo(pt(10f, 5f).x, pt(10f, 5f).y)
            cubicTo(
                pt(10f, 5f).x, pt(10f, 5f).y,
                pt(10f, 4f).x, pt(10f, 4f).y,
                pt(11f, 4f).x, pt(11f, 4f).y,
            )
            lineTo(pt(13f, 4f).x, pt(13f, 4f).y)
            cubicTo(
                pt(13f, 4f).x, pt(13f, 4f).y,
                pt(14f, 4f).x, pt(14f, 4f).y,
                pt(14f, 5f).x, pt(14f, 5f).y,
            )
            lineTo(pt(14f, 7f).x, pt(14f, 7f).y)

            moveTo(pt(7f, 7f).x, pt(7f, 7f).y)
            lineTo(pt(8f, 20f).x, pt(8f, 20f).y)
            cubicTo(
                pt(8f, 20f).x, pt(8f, 20f).y,
                pt(8f, 21f).x, pt(8f, 21f).y,
                pt(9f, 21f).x, pt(9f, 21f).y,
            )
            lineTo(pt(15f, 21f).x, pt(15f, 21f).y)
            cubicTo(
                pt(15f, 21f).x, pt(15f, 21f).y,
                pt(16f, 21f).x, pt(16f, 21f).y,
                pt(16f, 20f).x, pt(16f, 20f).y,
            )
            lineTo(pt(17f, 7f).x, pt(17f, 7f).y)
        }

        drawPath(path, color = tint, style = Stroke(width = 1.6f))
    }
}

/**
 * Decorative tentacle-like shapes peeking from the bottom corners, matching the Eldritch mockup.
 */
@Composable
private fun EldritchTentacles(modifier: Modifier = Modifier, mirrored: Boolean = false) {
    Canvas(modifier = modifier.size(width = 150.dp, height = 230.dp)) {
        val path = Path().apply {
            moveTo(20f, 200f)
            cubicTo(8f, 150f, 42f, 135f, 26f, 95f)
            cubicTo(14f, 60f, 46f, 45f, 40f, 10f)
            cubicTo(58f, 28f, 44f, 68f, 56f, 90f)
            cubicTo(68f, 112f, 38f, 140f, 46f, 200f)
            close()
        }
        val scaleFactor = size.width / 120f

        val sign = if (mirrored) -1f else 1f
        translate(left = if (mirrored) size.width else 0f, top = size.height - 30f * scaleFactor) {
            scale(scaleX = sign * scaleFactor, scaleY = scaleFactor, pivot = Offset.Zero) {
                translate(left = -40f, top = -200f) {
                    drawPath(path, color = Rust.copy(alpha = 0.16f))
                }
            }
        }
        rotate(degrees = if (mirrored) 8f else -8f, pivot = Offset(size.width * (if (mirrored) 1f else 0f), size.height)) {
            translate(left = if (mirrored) size.width * 0.66f else size.width * 0.34f, top = size.height - 25f * scaleFactor * 0.8f) {
                scale(scaleX = sign * scaleFactor * 0.73f, scaleY = scaleFactor * 0.83f, pivot = Offset.Zero) {
                    translate(left = -40f, top = -200f) {
                        drawPath(path, color = Rust.copy(alpha = 0.13f))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TestResolverContentBeforeRollPreview() {
    EhcTheme {
        TestResolverContent(
            uiModel = TestResolverUiModel(
                configuration = TestResolverUiModel.Configuration(
                    diceCount = 3,
                    selectedStatus = Status.None,
                    statuses = listOf(Status.Cursed, Status.None, Status.Blessed),
                ),
            ),
            onStatusSelected = {},
            onIncrementDiceCount = {},
            onDecrementDiceCount = {},
            onDieClick = {},
            onRollDiceClick = {},
            onClearClick = {},
        )
    }
}

@EhcPreview
@Composable
private fun TestResolverContentAfterRollPreview() {
    EhcTheme {
        TestResolverContent(
            uiModel = TestResolverUiModel(
                configuration = TestResolverUiModel.Configuration(
                    diceCount = 3,
                    selectedStatus = Status.None,
                    statuses = listOf(Status.Cursed, Status.None, Status.Blessed),
                ),
                testResult = TestResult(
                    dice = listOf(
                        TestResult.Die(roll = 2, selected = true),
                        TestResult.Die(roll = 5, selected = false),
                        TestResult.Die(roll = 6, selected = false),
                    ),
                    successCount = 2,
                ),
            ),
            onStatusSelected = {},
            onIncrementDiceCount = {},
            onDecrementDiceCount = {},
            onDieClick = {},
            onRollDiceClick = {},
            onClearClick = {},
        )
    }
}
