package github.hongbeomi.macgyver.camerax

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import com.google.android.gms.common.images.Size
import com.google.android.gms.vision.CameraSource
import github.hongbeomi.macgyver.mlkit.base.PreferenceUtils
import java.io.IOException

class CameraSourcePreview(context: Context, attrs: AttributeSet?) :
    ViewGroup(context, attrs) {

    private val surfaceView: SurfaceView
    private var startRequested: Boolean
    private var surfaceAvailable: Boolean
    private var cameraSource: CameraSource? = null
    private var overlay: GraphicOverlay? = null

    @Throws(IOException::class)
    private fun start(cameraSource: CameraSource?) {
        if (cameraSource == null) {
            stop()
        }
        this.cameraSource = cameraSource
        if (this.cameraSource != null) {
            startRequested = true
            startIfReady()
        }
    }

    @Throws(IOException::class)
    fun start(cameraSource: CameraSource?, overlay: GraphicOverlay?) {
        this.overlay = overlay
        start(cameraSource)
    }

    fun stop() {
        if (cameraSource != null) {
            cameraSource!!.stop()
        }
    }

    fun release() {
        if (cameraSource != null) {
            cameraSource!!.release()
            cameraSource = null
        }
        surfaceView.holder.surface.release()
    }

    @SuppressLint("MissingPermission")
    @Throws(IOException::class)
    private fun startIfReady() {
        if (startRequested && surfaceAvailable) {
            if (PreferenceUtils.isCameraLiveViewportEnabled(context)) {
                cameraSource!!.start(surfaceView.holder)
            } else {
                cameraSource!!.start()
            }
            requestLayout()
            if (overlay != null) {
                val size: Size = cameraSource!!.previewSize
                val min: Int = size.width.coerceAtMost(size.height)
                val max: Int = size.width.coerceAtLeast(size.height)
                if (isPortraitMode) {
                    // Swap width and height sizes when in portrait, since it will be rotated by
                    // 90 degrees
                    overlay?.setCameraInfo(min, max, cameraSource!!.cameraFacing)
                } else {
                    overlay?.setCameraInfo(max, min, cameraSource!!.cameraFacing)
                }
                overlay?.clear()
            }
            startRequested = false
        }
    }

    private inner class SurfaceCallback : SurfaceHolder.Callback {
        override fun surfaceCreated(surface: SurfaceHolder) {
            surfaceAvailable = true
            try {
                startIfReady()
            } catch (e: IOException) {
                Log.e(TAG, "Could not start camera source.", e)
            }
        }

        override fun surfaceDestroyed(surface: SurfaceHolder) {
            surfaceAvailable = false
        }

        override fun surfaceChanged(
            holder: SurfaceHolder,
            format: Int,
            width: Int,
            height: Int
        ) {
        }
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        var width = 320
        var height = 240
        if (cameraSource != null) {
            val size: Size? = cameraSource!!.previewSize
            if (size != null) {
                width = size.getWidth()
                height = size.getHeight()
            }
        }

        // Swap width and height sizes when in portrait, since it will be rotated 90 degrees
        if (isPortraitMode) {
            val tmp = width
            width = height
            height = tmp
        }
        val layoutWidth = right - left
        val layoutHeight = bottom - top

        // Computes height and width for potentially doing fit width.
        var childWidth = layoutWidth
        var childHeight =
            (layoutWidth.toFloat() / width.toFloat() * height).toInt()

        // If height is too tall using fit width, does fit height instead.
        if (childHeight > layoutHeight) {
            childHeight = layoutHeight
            childWidth = (layoutHeight.toFloat() / height.toFloat() * width) as Int
        }
        for (i in 0 until childCount) {
            getChildAt(i).layout(0, 0, childWidth, childHeight)
            Log.d(TAG, "Assigned view: $i")
        }
        try {
            startIfReady()
        } catch (e: IOException) {
            Log.e(TAG, "Could not start camera source.", e)
        }
    }

    private val isPortraitMode: Boolean
        get() {
            val orientation: Int = context.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                return false
            }
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                return true
            }
            Log.d(TAG, "isPortraitMode returning false by default")
            return false
        }

    companion object {
        private const val TAG = "MIDemoApp:Preview"
    }

    init {
        startRequested = false
        surfaceAvailable = false
        surfaceView = SurfaceView(context)
        surfaceView.holder.addCallback(SurfaceCallback())
        addView(surfaceView)
    }
}