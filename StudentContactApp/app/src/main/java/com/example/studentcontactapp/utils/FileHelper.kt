package com.example.studentcontactapp.utils

import android.content.Context
import java.io.File

class FileHelper {
    companion object {
        private fun getFileName(nim: String): String {
            return "note_$nim.txt"
        }

        fun saveNote(context: Context, nim: String, content: String) {
            val file = File(context.filesDir, getFileName(nim))
            file.writeText(content)
        }

        fun loadNote(context: Context, nim: String): String {
            val file = File(context.filesDir, getFileName(nim))
            return if (file.exists()) {
                file.readText()
            } else {
                ""
            }
        }

        fun deleteNote(context: Context, nim: String): Boolean {
            val file = File(context.filesDir, getFileName(nim))
            return file.delete()
        }

        fun isNoteExists(context: Context, nim: String): Boolean {
            val file = File(context.filesDir, getFileName(nim))
            return file.exists()
        }

        fun getFileSize(context: Context, nim: String): Long {
            val file = File(context.filesDir, getFileName(nim))
            return if (file.exists()) file.length() else 0L
        }
    }
}