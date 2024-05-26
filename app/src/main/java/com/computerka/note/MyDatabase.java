package com.computerka.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String NOTE_TABLE = "NOTE_TABLE";
    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String NOTE_DB = "note.db";
    public static final int VERSION = 1;

    private  Context context;
    public MyDatabase(@Nullable Context context) {
        super(context, NOTE_DB, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + NOTE_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT, " + DESCRIPTION + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+NOTE_TABLE);
        onCreate(db);
    }

    public long addNote(NoteModel model){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TITLE,model.getTitle());
        cv.put(DESCRIPTION,model.getDescription());

       return db.insert(NOTE_TABLE,null,cv);
    }

    public ArrayList<NoteModel> getAllNotes (){
        String query = "SELECT * FROM "+NOTE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<NoteModel> models = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);

                models.add(new NoteModel(id,title,description));
            }while (cursor.moveToNext());
        }else {
            Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
        }
    return models;
    }


    public boolean deleteNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + NOTE_TABLE + " WHERE " + ID + " = " + id;
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToNext()){
            return true;
        }else{
            return false;
        }
    }


    public int updateNote(String title, String description, int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TITLE,title);
        cv.put(DESCRIPTION,description);

       return db.update(NOTE_TABLE,cv,"ID = ?",new String[]{String.valueOf(id)});
    }


}
