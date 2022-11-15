package com.dgparkcode.note.domain.di

import com.dgparkcode.note.domain.repository.NoteRepository
import com.dgparkcode.note.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetAllNotesUseCase(noteRepository: NoteRepository): GetAllNotesUseCase {
        return GetAllNotesUseCaseImpl(noteRepository)
    }

    @Singleton
    @Provides
    fun provideAddNoteUseCase(noteRepository: NoteRepository): AddNoteUseCase {
        return AddNoteUseCaseImpl(noteRepository)
    }

    @Singleton
    @Provides
    fun provideRemoveNoteUseCase(noteRepository: NoteRepository): RemoveNoteUseCase {
        return RemoveNoteUseCaseImpl(noteRepository)
    }

    @Singleton
    @Provides
    fun provideGetNoteUseCase(noteRepository: NoteRepository): GetNoteUseCase {
        return GetNoteUseCaseImpl(noteRepository)
    }
}