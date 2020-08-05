package github.hongbeomi.macgyver.mlkit.base

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.media.Image
import androidx.annotation.GuardedBy
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import github.hongbeomi.macgyver.camerax.GraphicOverlay
import java.nio.ByteBuffer

abstract class VisionProcessorBase<T> : VisionImageProcessor, ImageAnalysis.Analyzer {

    // To keep the latest images and its metadata.
    @GuardedBy("this")
    private var latestImage: ByteBuffer? = null

    @GuardedBy("this")
    private var latestImageMetaData: ImageProxy? = null

    // To keep the images and metadata in process.
    @GuardedBy("this")
    private var processingImage: ByteBuffer? = null

    @GuardedBy("this")
    private var processingMetaData: ImageProxy? = null

//    @SuppressLint("UnsafeExperimentalUsageError")
//    override fun analyze(imageProxy: ImageProxy) {
//        val mediaImage = imageProxy.image
//        mediaImage?.let {
//
//            image = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
//        }
//    }

    @Synchronized
    override fun process(
        data: ByteBuffer,
        imageProxy: ImageProxy,
        graphicOverlay: GraphicOverlay
    ) {
        latestImage = data
        latestImageMetaData = imageProxy
        if (processingImage == null && processingMetaData == null) {
            processLatestImage(graphicOverlay)
        }
    }

    @Synchronized
    private fun processLatestImage(graphicOverlay: GraphicOverlay) {
        processingImage = latestImage
        processingMetaData = latestImageMetaData
        latestImage = null
        latestImageMetaData = null
        if (processingImage != null && processingMetaData != null) {
            processImage(processingImage!!, processingMetaData!!, graphicOverlay)
        }
    }

    private fun processImage(
        data: ByteBuffer,
        imageProxy: ImageProxy,
        graphicOverlay: GraphicOverlay
    ) {
        val bitmap = BitmapUtils.getBitmap(data, imageProxy)
        detectInVisionImage(
            bitmap,
            imageProxy,
            graphicOverlay
        )
    }

    private fun detectInVisionImage(
        originalCameraImage: Bitmap?,
        imageProxy: ImageProxy,
        graphicOverlay: GraphicOverlay
    ) {
        imageProxy.image?.let {
            detectInImage(it)
                .addOnSuccessListener { results ->
                    onSuccess(
                        originalCameraImage,
                        results,
                        imageProxy,
                        graphicOverlay
                    )
                    imageProxy.close()
                    processLatestImage(graphicOverlay)
                }
                .addOnFailureListener { e ->
                    onFailure(e)
                    imageProxy.close()
                }

        }
    }

    override fun stop() {}

    protected abstract fun detectInImage(image: Image): Task<T>

    /**
     * Callback that executes with a successful detection result.
     *
     * @param originalCameraImage hold the original image from camera, used to draw the background
     * image.
     */
    protected abstract fun onSuccess(
        originalCameraImage: Bitmap?,
        results: T,
        imageProxy: ImageProxy,
        graphicOverlay: GraphicOverlay
    )

    protected abstract fun onFailure(e: Exception)
}