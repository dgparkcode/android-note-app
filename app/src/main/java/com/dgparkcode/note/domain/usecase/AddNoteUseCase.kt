package com.dgparkcode.note.domain.usecase

import com.dgparkcode.note.domain.entity.Note
import com.dgparkcode.note.domain.error.InvalidNoteException
import com.dgparkcode.note.domain.repository.NoteRepository
import javax.inject.Inject

interface AddNoteUseCase {

    suspend operator fun invoke(note: Note)
}

class AddNoteUseCaseImpl @Inject constructor(
    private val noteRepository: NoteRepository
) : AddNoteUseCase {

    override suspend fun invoke(note: Note) {
        if (note.title.isBlank() or note.title.isEmpty()) {
            throw InvalidNoteException(message = "note title is required.")
        }
        if (note.content.isBlank() or note.content.isEmpty()) {
            throw InvalidNoteException(message = "note content is required.")
        }
        return noteRepository.addNote(note = note)
    }
}