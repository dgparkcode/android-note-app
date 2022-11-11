package com.dgparkcode.note.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgparkcode.note.domain.usecase.GetAllNotesUseCase
import com.dgparkcode.note.ui.common.UserMessage
import com.dgparkcode.note.ui.notelist.NoteItem
import com.dgparkcode.note.ui.notelist.NoteListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {

    private val _noteListUiState = MutableStateFlow(NoteListUiState())
    val noteListUiState get() = _noteListUiState.asStateFlow()

    init {
        requestAllNotes()
    }

    fun userMessageShown(messageId: Long) {
        viewModelScope.launch {
            if (messageId == noteListUiState.value.userMessage?.id) {
                _noteListUiState.update { state ->
                    state.copy(userMessage = null)
                }
            }
        }
    }

    private fun requestAllNotes() {
        viewModelScope.launch {
            _noteListUiState.update { state ->
                state.copy(isLoading = true)
            }

            getAllNotesUseCase()
                .onEach { notes ->
                    val noteItems = notes.map { note ->
                        NoteItem(
                            id = note.id,
                            title = note.title,
                            content = note.content,
                            onNoteClick = {
                                Log.d(TAG, "requestAllNotes: clicked note id: $note")
                            }
                        )
                    }
                    _noteListUiState.update { state ->
                        state.copy(
                            isLoading = false,
                            noteItems = noteItems
                        )
                    }
                }
                .catch { e ->
                    _noteListUiState.update { state ->
                        state.copy(
                            isLoading = false,
                            userMessage = UserMessage(
                                message = e.localizedMessage ?: "error.."
                            )
                        )
                    }
                }
                .launchIn(this)
        }
    }

    companion object {
        const val TAG = "NoteListViewModel"
    }
}