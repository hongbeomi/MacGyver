package github.hongbeomi.macgyver.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.camera.core.Camera
import androidx.camera.core.ImageAnalysis
import androidx.core.app.ActivityCompat
import github.hongbeomi.macgyver.R
import github.hongbeomi.macgyver.databinding.ActivityCameraBinding
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraActivity : BaseActivity() {

    private val binding by binding<ActivityCameraBinding>(R.layout.activity_camera)
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUEST_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // FIXME: 2020/08/04
        binding.buttonCameraCapture.setOnClickListener { takePhoto() }

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun startCamera() {
        // TODO: 2020/08/04
    }

    private fun takePhoto() {
        // TODO: 2020/08/04
    }

    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun allPermissionsGranted() = false

    companion object {
        private const val TAG = "CameraXBasic"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUEST_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }

}