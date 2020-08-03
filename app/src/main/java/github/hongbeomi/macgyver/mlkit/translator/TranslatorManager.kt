package github.hongbeomi.macgyver.mlkit.translator

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions

abstract class TranslatorManager(sourceLanguage: String, targetLanguage: String) {

    private val options = TranslatorOptions.Builder()
        .setSourceLanguage(sourceLanguage)
        .setTargetLanguage(targetLanguage)
        .build()
    private var conditions: DownloadConditions? = null
    private val translator: Translator = Translation.getClient(options)

    init {
        downloadModel { Log.d("translator", "download success") }
    }

    private fun downloadModel(doOnSuccess: (Void) -> Unit) {
        conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        translator.downloadModelIfNeeded()
            .addOnSuccessListener(doOnSuccess)
            .addOnFailureListener {
                TODO()
            }
    }

    fun startTranslate(
        text: String,
        doOnSuccess: (String) -> Unit
    ) {
        translator.translate(text)
            .addOnSuccessListener(doOnSuccess)
            .addOnFailureListener { TODO() }
    }

}