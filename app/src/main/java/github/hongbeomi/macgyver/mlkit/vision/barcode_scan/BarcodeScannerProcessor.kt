package github.hongbeomi.macgyver.mlkit.vision.barcode_scan

import android.graphics.Rect
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import github.hongbeomi.macgyver.camerax.BaseImageAnalyzer
import github.hongbeomi.macgyver.camerax.GraphicOverlay
import java.io.IOException

class BarcodeScannerProcessor(private val view: GraphicOverlay) :
    BaseImageAnalyzer<List<Barcode>>() {

    private val options = BarcodeScannerOptions.Builder().build()
    private val scanner = BarcodeScanning.getClient(options)


    override val graphicOverlay: GraphicOverlay
        get() = view

    override fun detectInImage(image: InputImage): Task<List<Barcode>> {
        return scanner.process(image)
    }

    override fun stop() {
        try {
            scanner.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close Barcode Scanner: $e")
        }
    }

    override fun onSuccess(
        results: List<Barcode>,
        graphicOverlay: GraphicOverlay,
        rect: Rect
    ) {
        graphicOverlay.clear()
        results.forEach {
            val barcodeGraphic = BarcodeGraphic(graphicOverlay, it, rect)
            graphicOverlay.add(barcodeGraphic)
        }
        graphicOverlay.postInvalidate()
    }

    override fun onFailure(e: Exception) {
        Log.w(TAG, "Barcode Scan failed.$e")
    }

    companion object {
        private const val TAG = "BarcodeScanProcessor"
    }

}