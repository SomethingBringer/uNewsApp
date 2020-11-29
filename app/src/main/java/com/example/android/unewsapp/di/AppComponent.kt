package com.example.android.unewsapp.di

import com.example.android.unewsapp.ui.fragments.converter.ConverterFragment
import com.example.android.unewsapp.ui.fragments.feed.NewsFeedFragment
import com.example.android.unewsapp.ui.fragments.index.StockIndexFragment
import com.example.android.unewsapp.ui.fragments.search.NewsSearchFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactoryModule::class, AppProviderModule::class])
interface AppComponent {

    fun inject(fragment: NewsFeedFragment)
    fun inject(fragment: ConverterFragment)
    fun inject(fragment: NewsSearchFragment)
    fun inject(fragment: StockIndexFragment)
}