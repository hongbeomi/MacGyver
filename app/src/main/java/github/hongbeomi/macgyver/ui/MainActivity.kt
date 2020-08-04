package github.hongbeomi.macgyver.ui

import android.content.Intent
import android.os.Bundle
import github.hongbeomi.macgyver.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(
            Intent(this, CameraActivity::class.java)
        )
    }

}