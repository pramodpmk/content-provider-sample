package com.hifx.syncadapterexample
/*
import android.content.Context
import androidx.room.Room
import com.hifx.syncadapterexample.ui.presentation.notes.db.NoteDAO
import com.hifx.syncadapterexample.ui.presentation.notes.db.NoteDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): NoteDataBase {
        return Room.databaseBuilder(
            context,
            NoteDataBase::class.java,
            "note_database",
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideDao(dataBase: NoteDataBase): NoteDAO {
        return dataBase.noteDao()
    }
}
*/