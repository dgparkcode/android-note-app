package com.dgparkcode.note.ui.addeditnote

sealed class AddEditNoteEvent {

    data class LoadNote(val noteId: Long) : AddEditNoteEvent()

    object UserDetailShown : AddEditNoteEvent()

    object UserMessageShown : AddEditNoteEvent()

    data class SaveNote(val title: String, val content: String) : AddEditNoteEvent()

    object DeleteNote : AddEditNoteEvent()
}
