package github.hongbeomi.macgyver.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image


fun Image.imageToBitmap(): Bitmap? {
    val buffer = this.planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)
}
