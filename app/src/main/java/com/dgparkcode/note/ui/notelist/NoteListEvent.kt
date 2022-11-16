package com.dgparkcode.note.ui.notelist

sealed class NoteListEvent {

    object LoadNotes : NoteListEvent()

    object UserMessageShown : NoteListEvent()

    data class RemoveNote(val id: Long) : NoteListEvent()
}
