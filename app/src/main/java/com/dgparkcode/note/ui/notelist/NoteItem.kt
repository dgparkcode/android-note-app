package com.dgparkcode.note.ui.notelist

import com.dgparkcode.note.domain.entity.Note

data class NoteItem(
    val id: Long,
    val title: String,
    val content: String
)

fun Note.toNoteItem(): NoteItem {
    return NoteItem(
        id = id,
        title = title,
        content = content
    )
}