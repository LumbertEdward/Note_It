package com.example.noteit.DatabaseClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.noteit.ModelClasses.ToDo;

import java.util.ArrayList;
import java.util.Random;

public class ToDoDatabase  extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ToDoDatabase";
    private static final String TABLE_NAME = "todo";
    private static final int VERSION = 1;
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_ID = "id";
    public ToDoDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATABASE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " TEXT PRIMARY KEY," + COLUMN_TITLE + " TEXT," + COLUMN_CATEGORY + " TEXT," + COLUMN_STATUS + " TEXT" + ")";
        db.execSQL(CREATE_DATABASE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

    public boolean addToDo(ToDo toDo){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, toDo.getId());
        contentValues.put(COLUMN_TITLE, toDo.getTitle());
        contentValues.put(COLUMN_CATEGORY, toDo.getCategory());
        contentValues.put(COLUMN_STATUS, toDo.getStatus());
        long out = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (out <= 0){
            return false;
        }
        else {
            return true;
        }
    }

    public ArrayList<ToDo> getToDo(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<ToDo> toDoArrayList = new ArrayList<>();
        while (cursor.moveToNext()){
            ToDo toDo = new ToDo();
            toDo.setId(cursor.getString(0));
            toDo.setTitle(cursor.getString(1));
            toDo.setCategory(cursor.getString(2));
            toDo.setStatus(cursor.getString(3));
            toDoArrayList.add(toDo);
        }
        return toDoArrayList;
    }
    public void updateData(String ID, String status){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String selection = COLUMN_ID + " =?";
        String[] selectionArgs = {ID};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, status);
        sqLiteDatabase.update(TABLE_NAME, contentValues, selection, selectionArgs);
        sqLiteDatabase.close();
    }
}
