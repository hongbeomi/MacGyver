package github.hongbeomi.macgyver.mlkit.image_label

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.google.mlkit.vision.label.ImageLabel
import github.hongbeomi.macgyver.camerax.GraphicOverlay

class LabelGraphic(
    private val overlay: GraphicOverlay,
    private val labels: List<ImageLabel>
) : GraphicOverlay.Graphic(overlay) {

    private val textPaint = Paint().apply {
        color = Color.WHITE
        textSize = 60.0f
    }

    @Synchronized
    override fun draw(canvas: Canvas?) {
        val x = overlay.width / 4.0f
        var y = overlay.height / 2.0f
        for (label in labels) {
            canvas?.drawText(label.text, x, y, textPaint)
            y -= 62.0f
        }
    }
}