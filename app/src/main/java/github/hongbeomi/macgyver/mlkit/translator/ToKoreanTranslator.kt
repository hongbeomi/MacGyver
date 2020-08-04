package github.hongbeomi.macgyver.mlkit.translator

import com.google.mlkit.nl.translate.TranslateLanguage

class ToKoreanTranslator: TranslatorProcessor(
    TranslateLanguage.ENGLISH,
    TranslateLanguage.KOREAN
)