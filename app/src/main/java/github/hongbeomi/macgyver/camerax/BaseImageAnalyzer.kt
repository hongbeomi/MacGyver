package github.hongbeomi.macgyver.camerax

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import kotlinx.coroutines.runBlocking
import java.nio.ByteBuffer

abstract class BaseImageAnalyzer<T> : ImageAnalysis.Analyzer {

    abstract var doOnSuccess: (List<T>) -> Unit
    abstract val process: Task<List<T>>?
    var image: InputImage? = null

    private fun Task<List<T>>.startProcess(closeProxy: () -> Unit) {
        this.addOnSuccessListener {
            doOnSuccess(it)
            closeProxy()
        }.addOnFailureListener {
            Log.e("analyzer error", it.toString())
            closeProxy()
        }
    }

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        mediaImage?.let {
            image = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
            process?.startProcess { imageProxy.close() }
        }
    }

}