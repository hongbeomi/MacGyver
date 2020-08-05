package github.hongbeomi.macgyver.camerax

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.media.Image
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabel
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions
import github.hongbeomi.macgyver.mlkit.base.BitmapUtils.toBitmap
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.nio.ByteBuffer

abstract class BaseImageAnalyzer<T> : ImageAnalysis.Analyzer {

//    abstract var doOnSuccess: (T) -> Unit
//    abstract val process: Task<T>?
    abstract val graphicOverlay : GraphicOverlay
//    var image: InputImage? = null

//    private fun Task<T>.startProcess(closeProxy: () -> Unit) {
//        this.addOnSuccessListener {
//            doOnSuccess(it)
//            closeProxy()
//        }.addOnFailureListener {
//            Log.e("analyzer error", it.toString())
//            closeProxy()
//        }
//    }

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        mediaImage?.let {
//            image = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)
//            process?.startProcess { imageProxy.close() }
            detectInImage(InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees))
                .addOnSuccessListener { results ->
                    onSuccess(
                        it.toBitmap(),
                        results,
                        imageProxy,
                        graphicOverlay
                    )
                    imageProxy.close()
                }
                .addOnFailureListener {
                    onFailure(it)
                    imageProxy.close()
                }
        }
    }

    protected abstract fun detectInImage(image: InputImage): Task<T>

    abstract fun stop()

    protected abstract fun onSuccess(
        originalCameraImage: Bitmap,
        results: T,
        imageProxy: ImageProxy,
        graphicOverlay: GraphicOverlay
    )

    protected abstract fun onFailure(e: Exception)

}