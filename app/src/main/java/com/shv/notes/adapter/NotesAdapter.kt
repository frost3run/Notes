package com.shv.notes.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.shv.notes.Note

class NotesAdapter(private val notes: ArrayList<Note>, private val onNoteClickListener: OnNoteClickListener) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    interface OnNoteClickListener {
        fun onNoteClick(position: Int)
        fun onNoteLongClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        //Log.i("Adapter", "onCreateViewHolder")
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        //Log.i("Adapter", "onBindViewHolder for position $position")
        val note: Note = notes[position]
        holder.textViewTitle?.text = note.title
        holder.textViewDescription?.text = note.description
        holder.textViewDayOfWeek?.text = Note.getDayAsString(note.dayOfWeek)
        Log.i("day", Note.getDayAsString(note.dayOfWeek))
        var colorId = 0
        when (note.priority) {
            1 -> colorId = holder.itemView.resources.getColor(android.R.color.holo_red_light)
            2 -> colorId = holder.itemView.resources.getColor(android.R.color.holo_orange_light)
            3 -> colorId = holder.itemView.resources.getColor(android.R.color.holo_green_light)
        }
        holder.textViewTitle?.setBackgroundColor(colorId)
        holder.itemView.setOnClickListener(View.OnClickListener {
            onNoteClickListener.onNoteClick(position)
        })
        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            onNoteClickListener.onNoteLongClick(position)
            return@OnLongClickListener true
        })
    }

    override fun getItemCount() = notes.size

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView? = null
        var textViewDescription: TextView? = null
        var textViewDayOfWeek: TextView? = null

        init {
            textViewTitle = itemView.findViewById(R.id.textViewTitle)
            textViewDescription = itemView.findViewById(R.id.textViewDescription)
            textViewDayOfWeek = itemView.findViewById(R.id.textViewDayOfWeek)
        }
    }
}