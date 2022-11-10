package com.dgparkcode.note.domain.repository

import com.dgparkcode.note.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun addNote(note: Note)

    suspend fun removeNote(note: Note)
}