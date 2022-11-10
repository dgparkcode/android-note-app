package com.dgparkcode.note.domain.usecase

import com.dgparkcode.note.domain.entity.Note
import com.dgparkcode.note.domain.repository.NoteRepository
import javax.inject.Inject

interface RemoveNoteUseCase {

    suspend operator fun invoke(note: Note)
}

class RemoveNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : RemoveNoteUseCase {

    override suspend fun invoke(note: Note) {
        return noteRepository.removeNote(note = note)
    }
}