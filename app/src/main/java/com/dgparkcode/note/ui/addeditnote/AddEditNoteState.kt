package com.dgparkcode.note.ui.addeditnote

import com.dgparkcode.note.ui.common.UserMessage

data class AddEditNoteState(
    val isLoading: Boolean = false,
    val isNoteSaved: Boolean = false,
    val isNoteRemoved: Boolean = false,
    val userMessage: UserMessage? = null
)
