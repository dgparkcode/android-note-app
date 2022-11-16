package com.dgparkcode.note.ui.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.dgparkcode.note.R
import com.dgparkcode.note.databinding.FragmentNoteListBinding
import com.dgparkcode.note.ui.extension.showOrHide
import com.dgparkcode.note.ui.viewmodel.NoteListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NoteListFragment : Fragment(R.layout.fragment_note_list) {

    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private val noteListViewModel: NoteListViewModel by viewModels()

    private lateinit var noteItemListAdapter: NoteItemListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNoteList()

        binding.fabAddNote.setOnClickListener {
            moveToAddEditNote()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteListViewModel.uiState.collect { state ->
                    binding.progressBar.showOrHide(state.isLoading)

                    state.userMessage?.let { userMsg ->
                        Toast.makeText(
                            requireContext(),
                            userMsg.message,
                            Toast.LENGTH_SHORT
                        ).show()

                        noteListViewModel.event(NoteListEvent.UserMessageShown)
                    }

                    noteItemListAdapter.submitList(state.noteItems)
                }
            }
        }
    }

    private fun setupNoteList() {
        with(binding.rvNoteList) {
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = NoteItemListAdapter(
                onItemClick = { note ->
                    moveToAddEditNote(note.id)
                },
                onDeleteClick = { note ->
                    noteListViewModel.event(
                        NoteListEvent.RemoveNote(note.id)
                    )
                }
            ).also { noteItemListAdapter = it }
        }
    }

    private fun moveToAddEditNote(noteId: Long? = null) {
        findNavController().navigate(
            NoteListFragmentDirections.actionNoteListFragmentToAddEditNoteFragment(
                noteId ?: 0L
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}