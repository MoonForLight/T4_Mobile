package com.example.studentcontactapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.adapter.NoteAdapter
import java.io.File

class NoteListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvNotes = view.findViewById<RecyclerView>(R.id.rvNotes)

        val fileNames: Array<String> = requireContext().fileList()

        val files = mutableListOf<File>()
        for (name in fileNames) {
            if (name.startsWith("note_")) {
                val file = File(requireContext().filesDir, name)
                files.add(file)
            }
        }

        rvNotes.layoutManager = LinearLayoutManager(requireContext())
        rvNotes.adapter = NoteAdapter(files)
    }
}