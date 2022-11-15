package com.dgparkcode.note.domain.usecase

import com.dgparkcode.note.domain.entity.Note
import com.dgparkcode.note.domain.repository.NoteRepository
import javax.inject.Inject

interface GetNoteUseCase {

    suspend operator fun invoke(id: Long): Note?
}

class GetNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : GetNoteUseCase {

    override suspend fun invoke(id: Long): Note? {
        return noteRepository.getNote(id)
    }
}