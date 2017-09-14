package ebj.awesome.yujinnotes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ebj.awesome.yujinnotes.model.Note;

/**
 * Created by Yujin on 14/09/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "YujinNotes.db";

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DELETED = "deleted";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NOTES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_DELETED + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public boolean insertNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        values.put(COLUMN_DELETED, note.isDeleted() ? 1 : 0);
        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_NOTES, null, values);
        return result != -1;
    }

    public boolean updateNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        values.put(COLUMN_DELETED, note.isDeleted() ? 1 : 0);
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = new String[] {Integer.toString(note.getId())};
        db.update(TABLE_NOTES, values, whereClause, whereArgs);
        return true;
    }

    public Integer deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = new String[] {Integer.toString(id)};
        return db.delete(TABLE_NOTES, whereClause, whereArgs);
    }

    public List<Note> getNotes (){
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NOTES;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            boolean deleted = cursor.getInt(cursor.getColumnIndex(COLUMN_DELETED)) == 1;
            Note note = new Note(id, title, description, deleted);
            notes.add(note);
            cursor.moveToNext();
        }
        return notes;
    }
}
