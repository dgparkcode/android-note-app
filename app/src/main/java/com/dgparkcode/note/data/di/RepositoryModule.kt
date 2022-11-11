package com.dgparkcode.note.data.di

import com.dgparkcode.note.data.local.dao.NoteEntityDao
import com.dgparkcode.note.data.repository.NoteRepositoryImpl
import com.dgparkcode.note.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideNoteRepository(noteEntityDao: NoteEntityDao): NoteRepository {
        return NoteRepositoryImpl(noteEntityDao)
    }
}