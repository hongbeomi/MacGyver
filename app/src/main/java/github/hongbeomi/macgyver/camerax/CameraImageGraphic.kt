package github.hongbeomi.macgyver.camerax

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

import github.hongbeomi.macgyver.camerax.GraphicOverlay.Graphic


class CameraImageGraphic(overlay: GraphicOverlay?, private val bitmap: Bitmap) : Graphic(
    overlay!!
) {
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(bitmap, null, Rect(0, 0, canvas.width, canvas.height), null)
    }

}