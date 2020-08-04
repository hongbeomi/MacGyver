package github.hongbeomi.macgyver.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import github.hongbeomi.macgyver.R
import github.hongbeomi.macgyver.mlkit.translator.ToEnglishTranslator

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}