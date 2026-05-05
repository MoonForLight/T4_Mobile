package com.example.studentcontactapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentcontactapp.utils.FileHelper

class DetailActivity : AppCompatActivity() {

    private lateinit var etNoteContent: EditText
    private lateinit var tvNoteStatus: TextView

    private var studentNim: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        studentNim = intent.getStringExtra("EXTRA_NIM") ?: ""
        val studentName = intent.getStringExtra("EXTRA_NAME") ?: "Detail Mahasiswa"
        val studentProdi = intent.getStringExtra("EXTRA_PRODI") ?: ""

        findViewById<TextView>(R.id.tvDetailName).text = studentName
        findViewById<TextView>(R.id.tvDetailInfo).text = "$studentNim · $studentProdi"

        etNoteContent = findViewById(R.id.etNoteContent)
        tvNoteStatus = findViewById(R.id.tvNoteStatus)

        val btnSaveNote = findViewById<Button>(R.id.btnSaveNote)
        val btnLoadNote = findViewById<Button>(R.id.btnLoadNote)

        checkAndLoadExistingNote()

        btnSaveNote.setOnClickListener {
            val content = etNoteContent.text.toString().trim()

            if (content.isEmpty()) {
                etNoteContent.error = "Catatan tidak boleh kosong"
                return@setOnClickListener
            }

            try {
                FileHelper.saveNote(this, studentNim, content)

                val size = FileHelper.getFileSize(this, studentNim)
                tvNoteStatus.visibility = View.VISIBLE
                tvNoteStatus.text = "✓ Tersimpan ($size bytes)"
                tvNoteStatus.setTextColor(android.graphics.Color.parseColor("#4CAF50"))

                Toast.makeText(this, "Catatan berhasil disimpan!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this, "Gagal menyimpan: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        btnLoadNote.setOnClickListener {
            checkAndLoadExistingNote()
            Toast.makeText(this, "Catatan dimuat ulang", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAndLoadExistingNote() {
        if (FileHelper.isNoteExists(this, studentNim)) {
            val savedNote = FileHelper.loadNote(this, studentNim)
            etNoteContent.setText(savedNote)

            val size = FileHelper.getFileSize(this, studentNim)
            tvNoteStatus.visibility = View.VISIBLE
            tvNoteStatus.text = "✓ Tersimpan ($size bytes)"
            tvNoteStatus.setTextColor(android.graphics.Color.parseColor("#4CAF50"))
        } else {
            tvNoteStatus.visibility = View.VISIBLE
            tvNoteStatus.text = "Belum ada catatan"
            tvNoteStatus.setTextColor(android.graphics.Color.GRAY)
        }
    }
}