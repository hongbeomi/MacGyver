package github.hongbeomi.macgyver.mlkit.translator

import com.google.mlkit.nl.translate.TranslateLanguage

class ToEnglishTranslator: TranslatorProcessor(
    TranslateLanguage.KOREAN,
    TranslateLanguage.ENGLISH
)