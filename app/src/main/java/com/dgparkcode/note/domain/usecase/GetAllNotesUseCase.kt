package com.dgparkcode.note.domain.usecase

import com.dgparkcode.note.domain.entity.Note
import com.dgparkcode.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetAllNotesUseCase {

    operator fun invoke(): Flow<List<Note>>
}

class GetAllNotesUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : GetAllNotesUseCase {

    override fun invoke(): Flow<List<Note>> {
        return noteRepository.getAllNotes()
    }
}