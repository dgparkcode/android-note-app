package com.dgparkcode.note.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgparkcode.note.domain.usecase.GetAllNotesUseCase
import com.dgparkcode.note.ui.common.UserMessage
import com.dgparkcode.note.ui.notelist.NoteListEvent
import com.dgparkcode.note.ui.notelist.NoteListState
import com.dgparkcode.note.ui.notelist.toNoteItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteListState())
    val uiState get() = _uiState.asStateFlow()

    init {
        event(NoteListEvent.LoadNotes)
    }

    fun event(event: NoteListEvent) {
        when (event) {
            NoteListEvent.LoadNotes -> loadNotes()
            NoteListEvent.UserMessageShown -> {
                _uiState.update { state ->
                    state.copy(userMessage = null)
                }
            }
        }
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isLoading = true)
            }

            try {
                getAllNotesUseCase().collect { notes ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            noteItems = notes.map { note -> note.toNoteItem() }
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        userMessage = UserMessage(
                            message = e.localizedMessage ?: "unknown error"
                        )
                    )
                }
            }
        }
    }
}