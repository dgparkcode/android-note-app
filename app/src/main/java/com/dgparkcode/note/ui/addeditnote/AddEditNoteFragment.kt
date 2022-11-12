package com.dgparkcode.note.ui.addeditnote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.dgparkcode.note.R
import com.dgparkcode.note.databinding.FragmentAddEditNoteBinding
import com.dgparkcode.note.ui.viewmodel.AddEditNoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    private val addEditNoteViewModel: AddEditNoteViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddEditNoteBinding.inflate(
            inflater,
            container,
            false
        )
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackPressedCallback()
        setupToolbarNavigation()
        setupToolbarMenu()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                addEditNoteViewModel.addEditNoteState.collectLatest { state ->
                    if (state.isLoading) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.INVISIBLE
                    }

                    if (state.userMessage != null) {
                        Toast.makeText(
                            requireContext(),
                            state.userMessage.message,
                            Toast.LENGTH_SHORT
                        ).show()

                        addEditNoteViewModel.userMessageShown(
                            state.userMessage.id
                        )
                    }

                    if (state.isNoteSaved) {
                        Toast.makeText(
                            requireContext(),
                            "Note Saved.",
                            Toast.LENGTH_SHORT
                        ).show()
                        navigateToNoteList()
                    }
                }
            }
        }
    }

    private fun setupBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateToNoteList()
        }
    }

    private fun navigateToNoteList() {
        findNavController().popBackStack()
    }

    private fun setupToolbarNavigation() {
        with(binding.toolbar.root) {
            setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            setNavigationOnClickListener {
                navigateToNoteList()
            }
        }
    }

    private fun setupToolbarMenu() {
        with(binding.toolbar.root) {
            inflateMenu(R.menu.add_edit_note)
            setOnMenuItemClickListener { menuItem ->
                if (menuItem.itemId == R.id.action_save) {
                    addEditNoteViewModel.onEvent(
                        AddEditNoteEvent.SaveNote(
                            title = binding.tieTitle.text?.toString() ?: "",
                            content = binding.tieContent.text?.toString() ?: ""
                        )
                    )
                    return@setOnMenuItemClickListener true
                }
                return@setOnMenuItemClickListener false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}