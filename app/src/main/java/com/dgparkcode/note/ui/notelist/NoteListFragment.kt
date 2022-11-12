package com.dgparkcode.note.ui.notelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.dgparkcode.note.R
import com.dgparkcode.note.databinding.FragmentNoteListBinding
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

        binding.rvNoteList.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        binding.rvNoteList.adapter = NoteItemListAdapter()
            .also { adapter ->
                noteItemListAdapter = adapter
            }

        binding.fabAddNote.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.addEditNoteFragment)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                noteListViewModel.noteListUiState.collect { state ->
                    if (state.isLoading) {
                        binding.progressBar.show()
                    } else {
                        binding.progressBar.hide()
                    }

                    if (state.userMessage != null) {
                        Log.d(TAG, "onViewCreated: user message received. message: ${state.userMessage.message}")

                        noteListViewModel.userMessageShown(
                            state.userMessage.id
                        )
                    }

                    noteItemListAdapter.submitList(
                        state.noteItems
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "NoteListFragment"
    }
}