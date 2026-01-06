package pl.parpok.doingsomethingtoday.wheelStuff

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

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

            // 1. Move to the center of the rect
            moveTo(center.x, center.y)

            // 2. Add the arc
            // Note: sweepAngle is the duration of the arc, not the end coordinate
            arcTo(
                rect = Rect(
                    center = center,
                    radius = radius
                ),
                startAngleDegrees = startAngle,
                sweepAngleDegrees = sweepAngle,
                forceMoveTo = false
            )

            // 3. Close back to the center
            lineTo(center.x, center.y)
            close()
        }
        return Outline.Generic(path)
    }
}

@Preview
@Composable
fun SliceShapePreview() {
    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.Red, shape = SliceShape(startAngle = 0f, sweepAngle = 90f))
    )
}