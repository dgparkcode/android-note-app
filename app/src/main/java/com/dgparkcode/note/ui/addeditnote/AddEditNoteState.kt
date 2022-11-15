package com.dgparkcode.note.ui.addeditnote

import com.dgparkcode.note.ui.common.UserMessage

data class AddEditNoteState(
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val noteDetail: NoteDetail? = null,
    val userMessage: UserMessage? = null
)
