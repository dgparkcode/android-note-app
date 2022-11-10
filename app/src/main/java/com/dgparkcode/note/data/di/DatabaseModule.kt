package com.dgparkcode.note.data.di

import android.content.Context
import androidx.room.Room
import com.dgparkcode.note.data.local.NoteDatabase
import com.dgparkcode.note.data.local.dao.NoteEntityDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext appContext: Context
    ): NoteDatabase {
        return Room
            .databaseBuilder(
                appContext,
                NoteDatabase::class.java,
                "note"
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideNoteEntityDao(noteDatabase: NoteDatabase): NoteEntityDao {
        return noteDatabase.noteEntityDao
    }
}