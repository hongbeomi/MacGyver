package github.hongbeomi.macgyver.camerax

import android.content.Context
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import github.hongbeomi.macgyver.mlkit.base.VisionType
import github.hongbeomi.macgyver.mlkit.vision.barcode_scan.BarcodeScannerProcessor
import github.hongbeomi.macgyver.mlkit.vision.face_detection.FaceContourDetectionProcessor
import github.hongbeomi.macgyver.mlkit.vision.object_detection.ObjectDetectionProcessor
import github.hongbeomi.macgyver.mlkit.vision.text_recognition.TextRecognitionProcessor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraManager(
    private val context: Context,
    private val finderView: PreviewView,
    private val lifecycleOwner: LifecycleOwner,
    private val graphicOverlay: GraphicOverlay
) {

    private var preview: Preview? = null

    private var camera: Camera? = null
    private lateinit var cameraExecutor: ExecutorService
    private var cameraSelectorOption = CameraSelector.LENS_FACING_BACK
    private var cameraProvider: ProcessCameraProvider? = null

    private var imageAnalyzer: ImageAnalysis? = null
    // default face contour detection
    private var analyzerVisionType: VisionType = VisionType.Face

    init {
        createNewExecutor()
    }

    private fun createNewExecutor() {
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener(
            Runnable {
                cameraProvider = cameraProviderFuture.get()
                preview = Preview.Builder()
                    .build()

                imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, selectAnalyzer())
                    }

                val cameraSelector = CameraSelector.Builder()
                    .requireLensFacing(cameraSelectorOption)
                    .build()
                setCameraConfig(cameraProvider, cameraSelector)

            }, ContextCompat.getMainExecutor(context)
        )
    }

    private fun selectAnalyzer(): ImageAnalysis.Analyzer {
        return when (analyzerVisionType) {
            VisionType.Object -> ObjectDetectionProcessor(graphicOverlay)
            VisionType.Text -> TextRecognitionProcessor(graphicOverlay)
            VisionType.Face -> FaceContourDetectionProcessor(graphicOverlay)
            VisionType.Barcode -> BarcodeScannerProcessor(graphicOverlay)
        }
    }

    private fun setCameraConfig(
        cameraProvider: ProcessCameraProvider?,
        cameraSelector: CameraSelector
    ) {
        try {
            cameraProvider?.unbindAll()
            camera = cameraProvider?.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalyzer
            )
            preview?.setSurfaceProvider(
                finderView.createSurfaceProvider()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Use case binding failed", e)
        }
    }

    fun changeCameraSelector(cameraSelector: Int) {
        cameraProvider?.unbindAll()
        cameraSelectorOption = cameraSelector
        startCamera()
    }

    fun changeAnalyzer(visionType: VisionType) {
        cameraProvider?.unbindAll()
        analyzerVisionType = visionType
        startCamera()
    }

    companion object {
        private const val TAG = "CameraXBasic"
    }

}