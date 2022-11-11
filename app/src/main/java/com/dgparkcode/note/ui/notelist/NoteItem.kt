package com.dgparkcode.note.ui.notelist

data class NoteItem(
    val id: Long,
    val title: String,
    val content: String,
    val onNoteClick: () -> Unit
)
