package com.shv.notes.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(NotesContract.NotesEntry.CREATE_COMMAND)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(NotesContract.NotesEntry.DROP_COMMAND)
        onCreate(db)
    }
    companion object {
        const val DATABASE_NAME = "NotesDB.db"
        const val DATABASE_VERSION = 2
    }
}