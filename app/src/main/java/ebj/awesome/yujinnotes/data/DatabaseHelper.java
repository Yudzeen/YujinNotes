package ebj.awesome.yujinnotes.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ebj.awesome.yujinnotes.model.Note;

public class DatabaseHelper extends SQLiteOpenHelper implements NotesRepository {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "YujinNotes.db";

    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_TRASHED = "trashed";
    private static final String COLUMN_POSITION = "position";

    private static DatabaseHelper instance;

    public synchronized static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NOTES + "(" +
                COLUMN_ID + " TEXT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_DESCRIPTION + " TEXT," +
                COLUMN_TRASHED + " INTEGER," +
                COLUMN_POSITION + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    @Override
    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NOTES;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
            boolean deleted = cursor.getInt(cursor.getColumnIndex(COLUMN_TRASHED)) == 1;
            int position = cursor.getInt(cursor.getColumnIndex(COLUMN_POSITION));
            Note note = new Note(id, title, description, deleted, position);
            notes.add(note);
            cursor.moveToNext();
        }

        cursor.close();
        return notes;
    }

    @Override
    public void insertNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, note.getId());
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_DESCRIPTION, note.getDescription());
        values.put(COLUMN_TRASHED, note.isTrashed() ? 1 : 0);
        values.put(COLUMN_POSITION, note.getPosition());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NOTES, null, values);
    }

    @Override
    public void updateNote(Note updatedNote) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, updatedNote.getTitle());
        values.put(COLUMN_DESCRIPTION, updatedNote.getDescription());
        values.put(COLUMN_TRASHED, updatedNote.isTrashed() ? 1 : 0);
        values.put(COLUMN_POSITION, updatedNote.getPosition());
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = new String[] {updatedNote.getId()};
        db.update(TABLE_NOTES, values, whereClause, whereArgs);
    }

    @Override
    public void deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_ID + " = ?";
        String[] whereArgs = new String[] {Integer.toString(id)};
        db.delete(TABLE_NOTES, whereClause, whereArgs);
    }

}
