//package github.hongbeomi.macgyver.mlkit.text_recognition
//
//import com.google.android.gms.tasks.Task
//import com.google.mlkit.vision.common.InputImage
//import com.google.mlkit.vision.label.ImageLabel
//import com.google.mlkit.vision.text.Text
//import com.google.mlkit.vision.text.TextRecognition
//import github.hongbeomi.macgyver.camerax.BaseImageAnalyzer
//
//class TextRecognitionProcessor (override var doOnSuccess: (Text) -> Unit) : BaseImageAnalyzer<Text>(){
//
//    private val recognizer = TextRecognition.getClient()
//
//    override val process: Task<Text>?
//        get() = image?.let { recognizer.process(it) }
//
//}