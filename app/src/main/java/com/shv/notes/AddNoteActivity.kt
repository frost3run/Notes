package com.shv.notes

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.notes.R
import com.shv.notes.db.NotesContract
import com.shv.notes.db.NotesDBHelper

class AddNoteActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var spinnerDayOfWeek: Spinner
    private lateinit var radioGroupPriority: RadioGroup

    private lateinit var dbHelper: NotesDBHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextDescription = findViewById(R.id.editTextDescription)
        spinnerDayOfWeek = findViewById(R.id.spinnerDayOfWeek)
        radioGroupPriority = findViewById(R.id.radioGroupPriority)
        dbHelper = NotesDBHelper(this)
        database = dbHelper.writableDatabase
    }

    fun onClickSaveNote(view: View) {
        val title = editTextTitle.editableText.toString()
        val description = editTextDescription.editableText.toString()
        val dayOfWeek = spinnerDayOfWeek.selectedItemPosition
        val radioButtonId = radioGroupPriority.checkedRadioButtonId
        val radioButton = findViewById<RadioButton>(radioButtonId)
        val priority = radioButton.text.toString().toInt()

        if (isField(title, description)) {
            val values = ContentValues().apply {
                put(NotesContract.NotesEntry.COLUMN_TITLE, title)
                put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, description)
                put(NotesContract.NotesEntry.COLUMN_DAY_OF_WEEK, dayOfWeek + 1)
                put(NotesContract.NotesEntry.COLUMN_PRIORITY, priority)
            }
            database.insert(NotesContract.NotesEntry.TABLE_NAME, null, values)

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, getString(R.string.warrning_fill_fields), Toast.LENGTH_SHORT).show()
        }
    }

    private fun isField(title: String, description: String): Boolean {
        return title.isNotEmpty() and description.isNotEmpty()
    }
}