package github.hongbeomi.macgyver.mlkit.translator

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.runBlocking

abstract class TranslatorProcessor(sourceLanguage: String, targetLanguage: String) {

    private val options = TranslatorOptions.Builder()
        .setSourceLanguage(sourceLanguage)
        .setTargetLanguage(targetLanguage)
        .build()
    lateinit var conditions: DownloadConditions
    private val translator: Translator = Translation.getClient(options)

    init {
        downloadModel()
    }

    private fun downloadModel() {
        conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener { Log.d("translator", "download success") }
            .addOnFailureListener { Log.e("??", it.toString()) }
    }

    fun startTranslate(
        text: String,
        doOnSuccess: (String) -> Unit
    ) {
        translator.translate(text)
            .addOnSuccessListener(doOnSuccess)
            .addOnFailureListener { Log.e("???????/", it.toString()) }
    }

}