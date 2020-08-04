package github.hongbeomi.macgyver.mlkit.barcode_scan

import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class BarcodeScannerManager {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE,
            Barcode.FORMAT_AZTEC
        )
        .build()
    val scanner = BarcodeScanning.getClient(options)

    fun loadProcess(image: InputImage, doOnSuccess: (MutableList<Barcode>) -> Unit) {
        scanner.process(image)
            .addOnSuccessListener(doOnSuccess)
            .addOnFailureListener {
                // TODO: 2020/08/04
            }
    }


}