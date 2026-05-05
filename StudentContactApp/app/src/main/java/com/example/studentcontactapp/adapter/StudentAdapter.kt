package com.example.studentcontactapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.res.ColorStateList
import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.R
import com.example.studentcontactapp.database.entity.StudentEntity

class StudentAdapter(
    private val onItemClick: (StudentEntity) -> Unit,
    private val onEditClick: (StudentEntity) -> Unit,
    private val onDeleteClick: (StudentEntity) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    private var studentList = emptyList<StudentEntity>()

    private val avatarColors = listOf("#3F51B5", "#E91E63", "#009688", "#FF9800", "#673AB7")

    fun setData(students: List<StudentEntity>) {
        this.studentList = students
        notifyDataSetChanged()
    }

    fun getStudentAt(position: Int): StudentEntity {
        return studentList[position]
    }

    class StudentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAvatar: TextView = view.findViewById(R.id.tvAvatar)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvNim: TextView = view.findViewById(R.id.tvNim)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentItem = studentList[position]

        holder.tvName.text = currentItem.name
        holder.tvNim.text = currentItem.nim

        val initials = currentItem.name.split(" ")
            .mapNotNull { it.firstOrNull()?.toString() }
            .take(2).joinToString("").uppercase()
        holder.tvAvatar.text = if (initials.isNotEmpty()) initials else "?"
        
        val color = Color.parseColor(avatarColors[position % avatarColors.size])
        holder.tvAvatar.backgroundTintList = ColorStateList.valueOf(color)

        holder.itemView.setOnClickListener { onItemClick(currentItem) }
        holder.btnEdit.setOnClickListener { onEditClick(currentItem) }
        holder.btnDelete.setOnClickListener { onDeleteClick(currentItem) }
    }

    override fun getItemCount(): Int = studentList.size
}