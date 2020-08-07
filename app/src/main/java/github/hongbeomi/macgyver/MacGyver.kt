package github.hongbeomi.macgyver

import android.app.Application
import github.hongbeomi.macgyver.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MacGyver : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MacGyver)
            modules(viewModelModule)
        }
    }

}