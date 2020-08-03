package github.hongbeomi.macgyver.mlkit.lang_id

import com.google.mlkit.nl.languageid.LanguageIdentification

class LangIdManager {

    private val languageIdentity = LanguageIdentification.getClient()

    fun getLanguageId(text: String, doOnSuccess: (String) -> Unit) {
        languageIdentity.identifyLanguage(text)
            .addOnSuccessListener {
                if (it == "und") {
                    TODO()
                }
                else { doOnSuccess.invoke(it) }
            }
            .addOnFailureListener {
                TODO()
            }
    }

}