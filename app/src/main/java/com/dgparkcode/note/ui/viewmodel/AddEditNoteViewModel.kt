package com.dgparkcode.note.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dgparkcode.note.domain.entity.Note
import com.dgparkcode.note.domain.error.InvalidNoteException
import com.dgparkcode.note.domain.usecase.AddNoteUseCase
import com.dgparkcode.note.domain.usecase.GetNoteUseCase
import com.dgparkcode.note.ui.addeditnote.AddEditNoteEvent
import com.dgparkcode.note.ui.addeditnote.AddEditNoteState
import com.dgparkcode.note.ui.addeditnote.toNoteDetail
import com.dgparkcode.note.ui.common.UserMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getNoteUseCase: GetNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditNoteState())
    val uiState get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            savedStateHandle.getStateFlow(NOTE_ID, NO_ID)
                .filter { it != NO_ID }
                .collect { loadNote(it) }
        }
    }

    fun event(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.LoadNote -> {
                savedStateHandle[NOTE_ID] = event.noteId
            }
            AddEditNoteEvent.UserDetailShown -> {
                _uiState.update { state ->
                    state.copy(noteDetail = null)
                }
            }
            AddEditNoteEvent.UserMessageShown -> {
                _uiState.update { state ->
                    state.copy(userMessage = null)
                }
            }
            is AddEditNoteEvent.SaveNote -> {
                _uiState.update { state ->
                    state.copy(isLoading = true)
                }

                viewModelScope.launch {
                    try {
                        addNoteUseCase(
                            Note(
                                id = savedStateHandle[NOTE_ID] ?: NO_ID,
                                title = event.title,
                                content = event.content
                            )
                        )

                        _uiState.update { state ->
                            state.copy(isLoading = false, isSaved = true)
                        }
                    } catch (e: InvalidNoteException) {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                userMessage = UserMessage(
                                    message = e.localizedMessage ?: "error"
                                )
                            )
                        }
                    } catch (e: Exception) {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                userMessage = UserMessage(
                                    message = e.localizedMessage ?: "error"
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadNote(id: Long) {
        _uiState.update { state ->
            state.copy(isLoading = true)
        }

        viewModelScope.launch {
            try {
                getNoteUseCase(id)?.let { note ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            noteDetail = note.toNoteDetail()
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        userMessage = UserMessage(
                            message = e.localizedMessage ?: "error"
                        )
                    )
                }
            }
        }
    }

    companion object {
        const val NOTE_ID = "noteId"
        const val NO_ID = 0L
    }
}