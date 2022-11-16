package com.dgparkcode.note.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dgparkcode.note.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteEntityDao {

    @Query("SELECT id, title, content FROM note")
    fun getAllNoteEntities(): Flow<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteEntity(noteEntity: NoteEntity)

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteNoteEntity(id: Long)

    @Query("SELECT id, title, content FROM note WHERE id = :id LIMIT 1")
    suspend fun getNoteEntity(id: Long): NoteEntity?
}