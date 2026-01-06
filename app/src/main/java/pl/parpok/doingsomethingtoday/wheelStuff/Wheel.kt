package pl.parpok.doingsomethingtoday.wheelStuff

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class SliceShape(
    private val startAngle: Float,
    private val sweepAngle: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val diameter = minOf(size.width, size.height)
            val radius = diameter / 2f
            val center = Offset(size.width / 2f, size.height / 2f)

            moveTo(center.x, center.y)
            arcTo(
                rect = Rect(
                    center = center,
                    radius = radius
                ),
                startAngleDegrees = startAngle,
                sweepAngleDegrees = sweepAngle,
                forceMoveTo = false
            )
            lineTo(center.x, center.y)
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun SliceWithText(
    modifier: Modifier = Modifier,
    startAngle: Float,
    sweepAngle: Float,
    text: String,
    color: Color
) {
    BoxWithConstraints(modifier = modifier) {
        // The background slice
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color, shape = SliceShape(startAngle, sweepAngle))
        ) {}

        // The text
        val midAngle = startAngle + sweepAngle / 2.0
        val midAngleRad = Math.toRadians(midAngle)

        val density = LocalDensity.current
        val radiusPx = with(density) { (minOf(maxWidth, maxHeight) / 2).toPx() }
        val textRadiusPx = radiusPx * 0.5f

        val offsetX = (textRadiusPx * cos(midAngleRad)).toFloat()
        val offsetY = (textRadiusPx * sin(midAngleRad)).toFloat()

        Text(
            text = text,
            modifier = Modifier
                .align(Alignment.Center)
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .rotate(midAngle.toFloat())
        )
    }
}

@Preview
@Composable
fun SliceShapePreview() {
    SliceWithText(
        modifier = Modifier.size(200.dp),
        startAngle = -135f,
        sweepAngle = 90f,
        text = "Hello",
        color = Color.Red
    )
}
