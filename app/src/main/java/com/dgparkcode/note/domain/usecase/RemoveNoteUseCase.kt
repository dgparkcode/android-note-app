package com.dgparkcode.note.domain.usecase

import com.dgparkcode.note.domain.repository.NoteRepository
import javax.inject.Inject

interface RemoveNoteUseCase {

    suspend operator fun invoke(id: Long)
}

class RemoveNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : RemoveNoteUseCase {

    override suspend fun invoke(id: Long) {
        return noteRepository.removeNote(id)
    }
}