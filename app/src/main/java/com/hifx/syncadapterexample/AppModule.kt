package com.hifx.syncadapterexample

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideApplication(@ApplicationContext app: Context): NoteApp {
        return app as NoteApp
    }

    @Provides
    fun provideContext(@ApplicationContext app: Context): Context {
        return app
    }
}
