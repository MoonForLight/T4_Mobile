package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.adapter.StudentAdapter
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.database.dao.StudentDao
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var studentDao: StudentDao
    private lateinit var adapter: StudentAdapter
    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        studentDao = AppDatabase.getDatabase(requireContext()).studentDao()

        val etSearch = view.findViewById<TextInputEditText>(R.id.etSearch)
        val rvSearch = view.findViewById<RecyclerView>(R.id.rvSearch)

        adapter = StudentAdapter(
            onItemClick = { student ->
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("EXTRA_NIM", student.nim)
                intent.putExtra("EXTRA_NAME", student.name)
                intent.putExtra("EXTRA_PRODI", student.prodi)
                startActivity(intent)
            },
            onEditClick = { student ->
                val intent = Intent(requireContext(), FormStudentActivity::class.java)
                intent.putExtra("EXTRA_ID", student.id)
                intent.putExtra("EXTRA_NAME", student.name)
                intent.putExtra("EXTRA_NIM", student.nim)
                intent.putExtra("EXTRA_PRODI", student.prodi)
                intent.putExtra("EXTRA_EMAIL", student.email)
                intent.putExtra("EXTRA_SEMESTER", student.semester)
                startActivity(intent)
            },
            onDeleteClick = {
                Toast.makeText(requireContext(), "Hapus melalui halaman Home", Toast.LENGTH_SHORT).show()
            }
        )

        rvSearch.layoutManager = LinearLayoutManager(requireContext())
        rvSearch.adapter = adapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    delay(300)
                    val keyword = s.toString()
                    if (keyword.isEmpty()) {
                        studentDao.getAllStudents().collect { adapter.setData(it) }
                    } else {
                        studentDao.searchStudents(keyword).collect { adapter.setData(it) }
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Initial Load
        lifecycleScope.launch {
            studentDao.getAllStudents().collect { adapter.setData(it) }
        }
    }
}