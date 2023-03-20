package com.aroniez.futaa.utils

import android.content.Context
import android.os.StrictMode
import android.util.Log
import com.aroniez.futaa.R
import com.aroniez.futaa.api.selectedLang
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import java.io.IOException


object TranslatorUtil {
    fun getTranslateService(context: Context): Translate? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            context.resources.openRawResource(R.raw.credentials).use {
                val myCredentials = GoogleCredentials.fromStream(it)
                val translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build()
                return translateOptions.service
            }
        } catch (ioe: IOException) {
            Log.d("TranslatorUtil", "IOException: $ioe")
            ioe.printStackTrace()
            return null
        }
    }

    fun translateText(unTranslatedText: String, translator: Translate?): String {
        var translatedText = ""
        if (translator != null) {
            val translation = translator.translate(unTranslatedText, Translate.TranslateOption.targetLanguage(selectedLang), Translate.TranslateOption.model("base"))
            translatedText = translation.translatedText
        } else {
            translatedText = unTranslatedText
        }
        return translatedText
    }


}