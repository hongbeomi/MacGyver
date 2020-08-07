package github.hongbeomi.macgyver.util

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("bind:onNavigationItemSelected")
fun setOnNavigationItemSelectedListener(
    view: BottomNavigationView,
    listener: BottomNavigationView.OnNavigationItemSelectedListener?
) {
    listener ?: return
    view.setOnNavigationItemSelectedListener(listener)
}

@BindingAdapter("bind:onFabClick")
fun setOnFabButtonClickListener(
    view: FloatingActionButton,
    listener: View.OnClickListener?
) {
    listener ?: return
    view.setOnClickListener(listener)
}