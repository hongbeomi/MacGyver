package github.hongbeomi.macgyver.camerax

import android.annotation.SuppressLint
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import java.nio.ByteBuffer
typealias LumaListener = (luma: Double) -> Unit
typealias imageListener = (image: InputImage) -> Unit

class LuminosityAnalyzer(
    private val lumaListener: LumaListener,
    private val imageListener: imageListener
): ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind()
        val data = ByteArray(remaining())
        get(data)
        return data
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val buffer = imageProxy.planes[0].buffer
        val data = buffer.toByteArray()
        val pixels = data.map { it.toInt() and 0xFF }
        val luma = pixels.average()
        val mediaImage = imageProxy.image
        mediaImage?.let {
            val image = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
            imageListener(image)
        }
        lumaListener(luma)
        imageProxy.close()
    }

}