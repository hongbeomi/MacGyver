package github.hongbeomi.macgyver.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

open class BaseActivity: AppCompatActivity() {

    protected fun <T : ViewDataBinding> binding(resId: Int): Lazy<T> =
        lazy { DataBindingUtil.setContentView<T>(this, resId) }

}