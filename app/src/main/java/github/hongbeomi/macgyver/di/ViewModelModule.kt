package github.hongbeomi.macgyver.di

import github.hongbeomi.macgyver.ui.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
}