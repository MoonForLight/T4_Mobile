package com.example.studentcontactapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.database.dao.StudentDao
import com.example.studentcontactapp.database.entity.StudentEntity
import kotlinx.coroutines.launch

class FormStudentActivity : AppCompatActivity() {

    private lateinit var studentDao: StudentDao

    private var currentStudentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_student)

        studentDao = AppDatabase.getDatabase(this).studentDao()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val etName = findViewById<EditText>(R.id.etName)
        val etNim = findViewById<EditText>(R.id.etNim)
        val spinnerProdi = findViewById<Spinner>(R.id.spinnerProdi)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etSemester = findViewById<EditText>(R.id.etSemester)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val prodiList = arrayOf("▼ Pilih Prodi", "Informatika", "Mesin", "Industri", "Arsitektur", "Sipil")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, prodiList)
        spinnerProdi.adapter = spinnerAdapter

        currentStudentId = intent.getIntExtra("EXTRA_ID", -1)
        if (currentStudentId != -1) {
            toolbar.title = "Edit Mahasiswa"
            etName.setText(intent.getStringExtra("EXTRA_NAME"))
            etNim.setText(intent.getStringExtra("EXTRA_NIM"))
            etEmail.setText(intent.getStringExtra("EXTRA_EMAIL"))
            etSemester.setText(intent.getStringExtra("EXTRA_SEMESTER"))

            val prodiPos = prodiList.indexOf(intent.getStringExtra("EXTRA_PRODI"))
            if (prodiPos > 0) spinnerProdi.setSelection(prodiPos)
        } else {
            toolbar.title = "Tambah Mahasiswa"
        }

        toolbar.setNavigationOnClickListener { finish() }

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val nim = etNim.text.toString().trim()
            val prodi = spinnerProdi.selectedItem.toString()
            val email = etEmail.text.toString().trim()
            val semester = etSemester.text.toString().trim()

            if (name.isEmpty() || nim.isEmpty() || email.isEmpty() || semester.isEmpty() || prodi == "▼ Pilih Prodi") {
                Toast.makeText(this, "Semua kolom harus diisi dengan benar!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                if (currentStudentId == -1) {
                    val newStudent = StudentEntity(name = name, nim = nim, prodi = prodi, email = email, semester = semester)
                    studentDao.insert(newStudent)
                    Toast.makeText(this@FormStudentActivity, "Berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                } else {
                    val updatedStudent = StudentEntity(id = currentStudentId, name = name, nim = nim, prodi = prodi, email = email, semester = semester)
                    studentDao.update(updatedStudent)
                    Toast.makeText(this@FormStudentActivity, "Berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                }

                finish()
            }
        }
    }
}