package github.hongbeomi.macgyver.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.view.View
import java.io.FileOutputStream

fun Bitmap.rotateFlipImage(degree: Float, isFrontMode: Boolean): Bitmap? {
    val realRotation = when (degree) {
        0f -> 90f
        90f -> 0f
        180f -> 270f
        else -> 180f
    }
    val matrix = Matrix().apply {
        if (isFrontMode) {
            preScale(-1.0f, 1.0f)
        }
        postRotate(realRotation)
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, false)
}

fun Bitmap.scaleImage(view: View, isHorizontalRotation: Boolean): Bitmap? {
    val ratio = view.width.toFloat() / view.height.toFloat()
    val newHeight = (view.width * ratio).toInt()

    return when (isHorizontalRotation) {
        true -> Bitmap.createScaledBitmap(this, view.width, newHeight, false)
        false -> Bitmap.createScaledBitmap(this, view.width, view.height, false)
    }
}

fun Bitmap.getBaseYByView(view: View, isHorizontalRotation: Boolean): Float {
    return when (isHorizontalRotation) {
        true -> (view.height.toFloat() / 2) - (this.height.toFloat() / 2)
        false -> 0f
    }
}

fun Bitmap.saveToGallery(context: Context) {
    makeTempFile().apply {
        FileOutputStream(this).run {
            this@saveToGallery.compress(Bitmap.CompressFormat.JPEG, 100, this)
            flush()
            close()
        }
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also {
            it.data = Uri.fromFile(this)
            context.sendBroadcast(it)
        }
    }
}