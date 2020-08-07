package github.hongbeomi.macgyver.mlkit.vision.object_detection

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import com.google.mlkit.vision.objects.DetectedObject
import github.hongbeomi.macgyver.camerax.GraphicOverlay

class ObjectGraphic(
    overlay: GraphicOverlay,
    private val visionObject: DetectedObject,
    private val imageRect: Rect
) : GraphicOverlay.Graphic(overlay) {

    private val boxPaint: Paint = Paint()
    private val textPaint: Paint

    init {
        boxPaint.color = Color.WHITE
        boxPaint.style = Paint.Style.FILL
        boxPaint.alpha = 70

        textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.textSize = TEXT_SIZE
    }

    override fun draw(canvas: Canvas?) {
        // change width & height because rocate
        val rect = calculateRect(
            imageRect.height().toFloat(),
            imageRect.width().toFloat(),
            visionObject.boundingBox
        )
        canvas?.drawRoundRect(rect, TEXT_ROUND_CORNER, TEXT_ROUND_CORNER, boxPaint)
        // Draws object text.
        if (visionObject.labels.isNotEmpty()) {
            canvas?.drawText(
                visionObject.labels[0].text,
                rect.left,
                rect.bottom,
                textPaint
            )
        }
    }

    companion object {
        private const val TEXT_SIZE = 54.0f
        private const val TEXT_ROUND_CORNER = 8f
    }

}