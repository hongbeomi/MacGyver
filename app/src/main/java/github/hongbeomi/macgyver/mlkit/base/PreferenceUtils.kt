package github.hongbeomi.macgyver.mlkit.base

import android.R
import android.content.Context
import android.preference.PreferenceManager


class PreferenceUtils {
    companion object {
        fun isCameraLiveViewportEnabled(context: Context): Boolean {
            val sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context)
            val prefKey = "camera"
            return sharedPreferences.getBoolean(prefKey, false)
        }
    }
}