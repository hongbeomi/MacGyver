package github.hongbeomi.macgyver.util

import com.google.android.material.floatingactionbutton.FloatingActionButton

fun FloatingActionButton.transform() {
    this.hide(object : FloatingActionButton.OnVisibilityChangedListener() {
        override fun onHidden(fab: FloatingActionButton?) {
            super.onHidden(fab)
            fab?.show()
        }
    })
}