package com.ergea.ridsnewsapp.di

import com.ergea.ridsnewsapp.data.repository.NewsRepository
import com.ergea.ridsnewsapp.data.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideNewsRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}