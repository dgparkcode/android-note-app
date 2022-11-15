package com.dgparkcode.note.ui.addeditnote

import com.dgparkcode.note.domain.entity.Note

data class NoteDetail(
    val id: Long,
    val title: String,
    val content: String
)

fun Note.toNoteDetail(): NoteDetail {
    return NoteDetail(
        id = id,
        title = title,
        content = content
    )
}