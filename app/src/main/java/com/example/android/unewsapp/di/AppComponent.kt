package com.example.android.unewsapp.di

import com.example.android.unewsapp.ui.fragments.NewsFeedFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactoryModule::class, AppProviderModule::class])
interface AppComponent {

    fun inject(fragment: NewsFeedFragment)
}