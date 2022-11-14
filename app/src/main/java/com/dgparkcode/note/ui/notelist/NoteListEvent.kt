package com.dgparkcode.note.ui.notelist

sealed class NoteListEvent {

    object LoadNotes : NoteListEvent()

    object UserMessageShown : NoteListEvent()
}
