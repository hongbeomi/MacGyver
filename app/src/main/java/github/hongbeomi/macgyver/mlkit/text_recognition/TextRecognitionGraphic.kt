package github.hongbeomi.macgyver.mlkit.text_recognition

import android.graphics.*
import com.google.mlkit.vision.text.Text
import github.hongbeomi.macgyver.camerax.GraphicOverlay

class TextRecognitionGraphic(
    overlay: GraphicOverlay,
    private val element: Text.TextBlock,
    private val imageRect: Rect
) : GraphicOverlay.Graphic(overlay) {

    private val rectPaint = Paint().apply {
        color = TEXT_COLOR
        style = Paint.Style.FILL
        alpha = 50
    }

    private val textPaint = Paint().apply {
        color = TEXT_COLOR
        textSize = TEXT_SIZE
    }

    override fun draw(canvas: Canvas?) {
        element.boundingBox?.let { box ->
            val rect = calculateRect(imageRect.height().toFloat(), imageRect.width().toFloat(), box)
            canvas?.drawRoundRect(rect, 8f, 8f, rectPaint)
            canvas?.drawText(element.text, rect.left, rect.bottom, textPaint)
        }
    }

    companion object {
        private const val TEXT_COLOR = Color.WHITE
        private const val TEXT_SIZE = 54.0f
    }
}