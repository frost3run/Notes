package com.shv.notes

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.BaseColumns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.shv.notes.adapter.NotesAdapter
import com.shv.notes.db.NotesContract
import com.shv.notes.db.NotesDBHelper

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var dbHelper: NotesDBHelper
    private val notes = ArrayList<Note>()
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
        recyclerViewNotes.layoutManager = LinearLayoutManager(this)

        dbHelper = NotesDBHelper(this)
        database = dbHelper.writableDatabase
        getData()

        recyclerViewNotes.adapter = NotesAdapter(notes, object : NotesAdapter.OnNoteClickListener {
            override fun onNoteClick(position: Int) {
                Toast.makeText(this@MainActivity, "Item position: $position", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onNoteLongClick(position: Int) {
                remove(position)
            }
        })

        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                remove(viewHolder.adapterPosition)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes)
    }

    private fun remove(position: Int) {
        val id = notes[position].id
        val where = "${BaseColumns._ID} = ?"
        val whereArgs = arrayOf(id.toString())
        database.delete(NotesContract.NotesEntry.TABLE_NAME, where, whereArgs)
        getData()
        recyclerViewNotes.adapter?.notifyDataSetChanged()
    }

    private fun getData() {
        notes.clear()
        val selection = "${NotesContract.NotesEntry.COLUMN_PRIORITY} < ?"
        val selectionArgs = arrayOf("2")
        val cursor = database.query(NotesContract.NotesEntry.TABLE_NAME,
            null, null, null, null, null, null
        )
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val title = getString(getColumnIndex(NotesContract.NotesEntry.COLUMN_TITLE))
                val description = getString(getColumnIndex(NotesContract.NotesEntry.COLUMN_DESCRIPTION))
                val dayOfWeek = getInt(getColumnIndex(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK))
                val priority = getInt(getColumnIndex(NotesContract.NotesEntry.COLUMN_PRIORITY))
                val note = Note(id, title, description, dayOfWeek, priority)
                notes.add(note)
            }
        }
        cursor.close()
    }

    private fun delFromDB() {
        database.delete(NotesContract.NotesEntry.TABLE_NAME, null, null)
    }


    fun onClickAddNote(view: View) {
        val intent: Intent = Intent(this, AddNoteActivity::class.java)
        startActivity(intent)
    }
}