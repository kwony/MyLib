package com.kwony.mylib.base

import android.content.Context
import com.bumptech.glide.Glide
import com.kwony.data.DataModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module(includes = [
    DataModule::class
])
@InstallIn(SingletonComponent::class)
class BaseModule {
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context
}