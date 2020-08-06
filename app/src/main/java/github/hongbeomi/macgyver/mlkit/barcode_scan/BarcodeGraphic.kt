package github.hongbeomi.macgyver.mlkit.barcode_scan

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import com.google.mlkit.vision.barcode.Barcode
import github.hongbeomi.macgyver.camerax.GraphicOverlay

class BarcodeGraphic(
    overlay: GraphicOverlay,
    private val barcode: Barcode,
    private val imageRect: Rect
) : GraphicOverlay.Graphic(overlay) {

    private var rectPaint = Paint().apply {
        color = TEXT_COLOR
        style = Paint.Style.STROKE
        strokeWidth = STROKE_WIDTH
    }

    private var barcodePaint = Paint().apply {
        color = TEXT_COLOR
        textSize = TEXT_SIZE
    }

    override fun draw(canvas: Canvas?) {
        // Draws the bounding box around the BarcodeBlock.
        barcode.boundingBox?.let { box ->

            val rect = calculateRect(
                imageRect.height().toFloat(),
                imageRect.width().toFloat(),
                box
            )
            canvas?.drawRoundRect(rect, 8f, 8f, rectPaint)

            // Renders the barcode at the bottom of the box.
            barcode.rawValue?.let { value ->
                canvas?.drawText(value, rect.left, rect.bottom, barcodePaint)
            }
        }

    }

    companion object {
        private const val TEXT_COLOR = Color.WHITE
        private const val TEXT_SIZE = 54.0f
        private const val STROKE_WIDTH = 4.0f
    }

}