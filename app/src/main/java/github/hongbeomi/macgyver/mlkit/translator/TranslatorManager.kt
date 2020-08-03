package github.hongbeomi.macgyver.mlkit.translator

import android.util.Log
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import java.lang.Exception

abstract class TranslatorManager(sourceLanguage: String, targetLanguage: String) {

    private val options = TranslatorOptions.Builder()
        .setSourceLanguage(sourceLanguage)
        .setTargetLanguage(targetLanguage)
        .build()
    private var conditions: DownloadConditions? = null
    private val translator: Translator = Translation.getClient(options)

    init {
        downloadModel({
            Log.d("translator", "download success")
        }, {
            Log.d("translator", "download fail")
        })
    }

    private fun downloadModel(doOnSuccess: (Void) -> Unit, doOnFailure: (Exception) -> Unit) {
        conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        translator.downloadModelIfNeeded()
            .addOnSuccessListener(doOnSuccess)
            .addOnFailureListener(doOnFailure)
    }

    fun startTranslate(
        text: String,
        doOnSuccess: (String) -> Unit,
        doOnFailure: (Exception) -> Unit
    ) {
        translator.translate(text)
            .addOnSuccessListener(doOnSuccess)
            .addOnFailureListener(doOnFailure)
    }

}