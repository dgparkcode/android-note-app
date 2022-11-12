package com.dgparkcode.note.ui.addeditnote

sealed class AddEditNoteEvent {

    data class SaveNote(val title: String, val content: String) : AddEditNoteEvent()
}
