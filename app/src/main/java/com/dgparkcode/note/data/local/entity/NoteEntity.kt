package com.dgparkcode.note.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dgparkcode.note.domain.entity.Note

@Entity(tableName = "note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val content: String
)

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        content = content
    )
}