package com.example.android.unewsapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.unewsapp.ui.fragments.feed.NewsFeedViewModel
import com.example.android.unewsapp.ui.fragments.search.NewsSearchViewModel
import com.example.android.unewsapp.utils.viewmodel.ViewModelKey
import com.example.android.unewsapp.utils.viewmodel.ViewModelProviderFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

    @Binds
    internal abstract fun bind(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    abstract fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(NewsSearchViewModel::class)
    abstract fun bindNewsSearchViewModel(viewModel: NewsSearchViewModel): ViewModel
}