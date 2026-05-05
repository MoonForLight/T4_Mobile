package com.example.studentcontactapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.adapter.StudentAdapter
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.database.dao.StudentDao
import com.example.studentcontactapp.database.entity.StudentEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var studentDao: StudentDao
    private lateinit var adapter: StudentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getDatabase(requireContext())
        studentDao = database.studentDao()

        val rvStudents = view.findViewById<RecyclerView>(R.id.rvStudents)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)

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
            onDeleteClick = { student ->
                showDeleteConfirmationDialog(student.id, student.name)
            }
        )

        rvStudents.layoutManager = LinearLayoutManager(requireContext())
        rvStudents.adapter = adapter

        // Swipe to Delete
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val student = adapter.getStudentAt(position)
                showDeleteConfirmationDialog(student.id, student.name, position)
            }
        }).attachToRecyclerView(rvStudents)

        lifecycleScope.launch {
            if (studentDao.getStudentCount() == 0) {
                val sampleData = listOf(
                    StudentEntity(name = "Ahmad Fauzi", nim = "2024001", prodi = "Informatika", email = "ahmad@mail.com", semester = "5"),
                    StudentEntity(name = "Budi Santoso", nim = "2024002", prodi = "Mesin", email = "budi@mail.com", semester = "3"),
                    StudentEntity(name = "Citra Lestari", nim = "2024003", prodi = "Sipil", email = "citra@mail.com", semester = "7")
                )
                studentDao.insertAll(sampleData)
            }

            studentDao.getAllStudents().collect { studentList ->
                adapter.setData(studentList)
            }
        }

        fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), FormStudentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDeleteConfirmationDialog(studentId: Int, studentName: String, position: Int? = null) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Hapus Data?")
        builder.setMessage("Hapus \"$studentName\"? Tindakan ini tidak dapat dibatalkan.")

        builder.setPositiveButton("Hapus") { dialog, _ ->
            lifecycleScope.launch {
                studentDao.deleteById(studentId)
                Toast.makeText(requireContext(), "$studentName berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Batal") { dialog, _ ->
            position?.let { adapter.notifyItemChanged(it) }
            dialog.dismiss()
        }

        builder.setOnCancelListener {
            position?.let { adapter.notifyItemChanged(it) }
        }

        val dialog = builder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#D32F2F"))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#757575"))
        }
        dialog.show()
    }
}