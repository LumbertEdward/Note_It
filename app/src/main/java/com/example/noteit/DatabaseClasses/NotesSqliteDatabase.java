package com.example.noteit.DatabaseClasses;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.noteit.ModelClasses.Notes;

import java.util.ArrayList;
import java.util.Random;

public class NotesSqliteDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NotesDatabase";
    private static final String TABLE_NAME = "NotesAll";
    private static final int VERSION = 1;
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_ID = "id";
    public NotesSqliteDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATABASE = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " TEXT PRIMARY KEY," +  COLUMN_TITLE + " TEXT," + COLUMN_NOTE + " TEXT," +
                COLUMN_TIME + " TEXT" + ")";
        db.execSQL(CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean addNote(String title, String note, String time){
        Random random = new Random();
        int id = random.nextInt(1000);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_NOTE, note);
        contentValues.put(COLUMN_TIME, time);
        contentValues.put(COLUMN_ID, String.valueOf(id));
        long v = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (v <= 0){
            return false;
        }
        else {
            return true;
        }
    }
    public ArrayList<Notes> getNotes(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Notes> notes = new ArrayList<>();
        while(cursor.moveToNext()){
            Notes notes1 = new Notes();
            notes1.setId(cursor.getString(0));
            notes1.setTitle(cursor.getString(1));
            notes1.setNote(cursor.getString(2));
            notes1.setTime(cursor.getString(3));
            notes.add(notes1);
        }
        //cursor.close();
        //sqLiteDatabase.close();
        return notes;
    }
    public void deleteNote(String ID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {ID};
        sqLiteDatabase.delete(TABLE_NAME, selection, selectionArgs);
        sqLiteDatabase.close();
    }
    public void updateNote(String title, String note, String time, String ID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String selection = COLUMN_ID + "=?";
        String[] selectionArgs = {ID};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_NOTE, note);
        contentValues.put(COLUMN_TIME, time);
        sqLiteDatabase.update(TABLE_NAME, contentValues, selection, selectionArgs);
        sqLiteDatabase.close();
    }
}
