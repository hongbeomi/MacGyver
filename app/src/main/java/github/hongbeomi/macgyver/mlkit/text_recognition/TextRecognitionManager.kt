package github.hongbeomi.macgyver.mlkit.text_recognition

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition

class TextRecognitionManager {

    private val recognizer = TextRecognition.getClient()

    fun loadProcess(image: InputImage, doOnSuccess: (Text) -> Unit) {
        recognizer.process(image)
            .addOnSuccessListener(doOnSuccess)
            .addOnFailureListener {
                // TODO: 2020/08/04
            }
    }


}