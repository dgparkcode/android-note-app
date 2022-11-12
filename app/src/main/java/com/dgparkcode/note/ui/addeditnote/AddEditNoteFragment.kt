package com.dgparkcode.note.ui.addeditnote

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dgparkcode.note.R
import com.dgparkcode.note.databinding.FragmentAddEditNoteBinding

class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

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

        val toolbar = binding.toolbar.root
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            Log.d(TAG, "onViewCreated: back button clicked.")
        }
        toolbar.inflateMenu(R.menu.add_edit_note)
        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.action_save) {
                Log.d(TAG, "onViewCreated: save menu button clicked.")
                return@setOnMenuItemClickListener true
            }
            return@setOnMenuItemClickListener false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "AddEditNoteFragment"
    }
}