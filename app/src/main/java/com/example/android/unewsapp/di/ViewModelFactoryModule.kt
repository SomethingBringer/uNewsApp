package com.example.android.unewsapp.di

import androidx.lifecycle.ViewModelProvider
import com.example.android.unewsapp.utils.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bind(factory: ViewModelProviderFactory): ViewModelProvider.Factory

}