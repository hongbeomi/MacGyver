package github.hongbeomi.macgyver.mlkit.base

import android.graphics.Bitmap
import androidx.camera.core.ImageProxy
import com.google.mlkit.common.MlKitException
import github.hongbeomi.macgyver.camerax.GraphicOverlay
import java.nio.ByteBuffer


interface VisionImageProcessor {
    /** Processes the images with the underlying machine learning models.  */
    @Throws(MlKitException::class)
    fun process(
        data: ByteBuffer,
        imageProxy: ImageProxy,
        graphicOverlay: GraphicOverlay
    )

    /** Stops the underlying machine learning model and release resources.  */
    fun stop()
}