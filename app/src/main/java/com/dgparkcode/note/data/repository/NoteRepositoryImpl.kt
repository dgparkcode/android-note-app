package com.dgparkcode.note.data.repository

import com.dgparkcode.note.data.local.dao.NoteEntityDao
import com.dgparkcode.note.data.local.entity.toNote
import com.dgparkcode.note.data.local.entity.toNoteEntity
import com.dgparkcode.note.domain.entity.Note
import com.dgparkcode.note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteEntityDao: NoteEntityDao
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteEntityDao.getAllNoteEntities()
            .map { entities ->
                entities.map { entity ->
                    entity.toNote()
                }
            }
    }

    override suspend fun addNote(note: Note) {
        noteEntityDao.insertNoteEntity(note.toNoteEntity())
    }

    override suspend fun removeNote(note: Note) {
        noteEntityDao.deleteNoteEntity(note.toNoteEntity())
    }

    override suspend fun getNote(id: Long): Note? {
        return noteEntityDao.getNoteEntity(id)?.toNote()
    }
}