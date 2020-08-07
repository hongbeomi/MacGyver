package github.hongbeomi.macgyver.mlkit.vision.text_recognition

import android.graphics.Rect
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import github.hongbeomi.macgyver.camerax.BaseImageAnalyzer
import github.hongbeomi.macgyver.camerax.GraphicOverlay
import java.io.IOException

class TextRecognitionProcessor(private val view: GraphicOverlay) : BaseImageAnalyzer<Text>() {

    private val recognizer = TextRecognition.getClient()
    override val graphicOverlay: GraphicOverlay
        get() = view

    override fun detectInImage(image: InputImage): Task<Text> {
        return recognizer.process(image)
    }

    override fun stop() {
        try {
            recognizer.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close Text Recognition: $e")
        }
    }

    override fun onSuccess(
        results: Text,
        graphicOverlay: GraphicOverlay,
        rect: Rect
    ) {
        graphicOverlay.clear()
        results.textBlocks.forEach {
            val textGraphic = TextRecognitionGraphic(graphicOverlay, it, rect)
            graphicOverlay.add(textGraphic)
        }
        graphicOverlay.postInvalidate()
    }

    override fun onFailure(e: Exception) {
        Log.w(TAG, "Text Recognition failed.$e")
    }

    companion object {
        private const val TAG = "TextProcessor"
    }

}