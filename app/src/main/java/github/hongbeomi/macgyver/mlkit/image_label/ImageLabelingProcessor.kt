package github.hongbeomi.macgyver.mlkit.image_label

import android.graphics.Rect
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import github.hongbeomi.macgyver.camerax.BaseImageAnalyzer
import github.hongbeomi.macgyver.camerax.GraphicOverlay
import java.io.IOException

class ImageLabelingProcessor(private val view: GraphicOverlay) :
    BaseImageAnalyzer<List<ImageLabel>>() {

    private val options = ImageLabelerOptions.Builder().setConfidenceThreshold(0.5f).build()
    private val labeler = ImageLabeling.getClient(options)

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
        results: List<ImageLabel>,
        graphicOverlay: GraphicOverlay,
        rect: Rect
    ) {
        graphicOverlay.clear()
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