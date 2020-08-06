//package github.hongbeomi.macgyver.mlkit.barcode_scan
//
//import com.google.android.gms.tasks.Task
//import com.google.mlkit.vision.barcode.Barcode
//import com.google.mlkit.vision.barcode.BarcodeScannerOptions
//import com.google.mlkit.vision.barcode.BarcodeScanning
//import github.hongbeomi.macgyver.camerax.BaseImageAnalyzer
//
//class BarcodeScannerProcessor(override var doOnSuccess: (List<Barcode>) -> Unit) : BaseImageAnalyzer<List<Barcode>>() {
//
//    private val options = BarcodeScannerOptions.Builder()
//        .setBarcodeFormats(
//            Barcode.FORMAT_QR_CODE,
//            Barcode.FORMAT_AZTEC
//        )
//        .build()
//    private val scanner = BarcodeScanning.getClient(options)
//
//    override val process: Task<List<Barcode>>?
//        get() = image?.let { scanner.process(it) }
//
//}