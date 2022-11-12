package com.dgparkcode.note.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgparkcode.note.domain.entity.Note
import com.dgparkcode.note.domain.error.InvalidNoteException
import com.dgparkcode.note.domain.usecase.AddNoteUseCase
import com.dgparkcode.note.ui.addeditnote.AddEditNoteEvent
import com.dgparkcode.note.ui.addeditnote.AddEditNoteState
import com.dgparkcode.note.ui.common.UserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {

    private val _addEditNoteState = MutableStateFlow(AddEditNoteState())
    val addEditNoteState get() = _addEditNoteState.asStateFlow()

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.SaveNote -> {
                requestSaveNote(event.title, event.content)
            }
        }
    }

    fun userMessageShown(messageId: Long) {
        viewModelScope.launch {
            if (messageId == addEditNoteState.value.userMessage?.id) {
                _addEditNoteState.emit(AddEditNoteState())
            }
        }
    }

    private fun requestSaveNote(title: String, content: String) {
        viewModelScope.launch {
            try {
                _addEditNoteState.emit(
                    AddEditNoteState(isLoading = true)
                )

                addNoteUseCase(
                    note = Note(
                        id = 0,
                        title = title,
                        content = content
                    )
                )
                _addEditNoteState.emit(
                    AddEditNoteState(isNoteSaved = true)
                )
            } catch (e: InvalidNoteException) {
                _addEditNoteState.emit(
                    AddEditNoteState(
                        userMessage = UserMessage(
                            message = e.localizedMessage ?: "error"
                        )
                    )
                )
            }
        }
    }
}