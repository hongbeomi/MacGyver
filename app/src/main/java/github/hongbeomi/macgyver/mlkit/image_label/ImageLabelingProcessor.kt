package github.hongbeomi.macgyver.mlkit.image_label

import android.graphics.Bitmap
import android.util.Log
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import github.hongbeomi.macgyver.camerax.BaseImageAnalyzer
import github.hongbeomi.macgyver.camerax.CameraImageGraphic
import github.hongbeomi.macgyver.camerax.GraphicOverlay
import java.io.IOException
import java.lang.Exception

class ImageLabelingProcessor(private val view: GraphicOverlay) : BaseImageAnalyzer<List<ImageLabel>>() {

    private val options = ImageLabelerOptions.Builder().setConfidenceThreshold(0.5f).build()
    private val labeler = ImageLabeling.getClient(options)

//    override val process: Task<List<ImageLabel>>?
//        get() = image?.let { labeler.process(it) }

    override val graphicOverlay: GraphicOverlay
        get() = view

    override fun detectInImage(image: InputImage): Task<List<ImageLabel>> {
        return labeler.process(image)
    }

    override fun stop() {
        try {
            labeler.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close Text Detector: $e")
        }
    }

    override fun onSuccess(
        originalCameraImage: Bitmap,
        results: List<ImageLabel>,
        imageProxy: ImageProxy,
        graphicOverlay: GraphicOverlay
    ) {
        graphicOverlay.clear()
        originalCameraImage.let { image ->
            val imageGraphic = CameraImageGraphic(graphicOverlay, image)
            graphicOverlay.add(imageGraphic)
        }
        val labelGraphic = LabelGraphic(graphicOverlay, results)
        graphicOverlay.add(labelGraphic)
        graphicOverlay.postInvalidate()

    }

    override fun onFailure(e: Exception) {
        Log.w(TAG, "Label detection failed.$e")
    }

    companion object {
        private const val TAG = "ImageLabelingProcessor"
    }

}