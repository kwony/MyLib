package com.kwony.mylib

import android.content.Context
import com.bumptech.glide.Glide
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
class BaseModule {
    @Provides
    fun provideRequestManager(@ActivityContext context: Context) = Glide.with(context)
}