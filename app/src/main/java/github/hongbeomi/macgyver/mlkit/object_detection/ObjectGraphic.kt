package github.hongbeomi.macgyver.mlkit.object_detection

import android.graphics.*
import android.util.Log
import com.google.mlkit.vision.objects.DetectedObject
import github.hongbeomi.macgyver.camerax.GraphicOverlay

class ObjectGraphic (
    overlay: GraphicOverlay,
    private val visionObject: DetectedObject,
    private val imageRect: Rect
) : GraphicOverlay.Graphic(overlay) {

    private val boxPaint: Paint = Paint()
    private val textPaint: Paint

    init {
        boxPaint.color = Color.WHITE
        boxPaint.style = Paint.Style.FILL
        boxPaint.alpha = 50

        textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.textSize = TEXT_SIZE
    }

    override fun draw(canvas: Canvas?) {
        // change width & height because rocate
        val rect = calculateRect(imageRect.height().toFloat(), imageRect.width().toFloat(), visionObject.boundingBox)
        canvas?.drawRoundRect(rect, 8f, 8f, boxPaint)

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
    }
}