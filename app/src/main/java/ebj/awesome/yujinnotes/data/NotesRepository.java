package ebj.awesome.yujinnotes.data;

import java.util.List;

import ebj.awesome.yujinnotes.model.Note;

/**
 * Created by Yujin on 20/09/2017.
 */

public interface NotesRepository {

    Note getNote(String id);
    List<Note> getNotes();
    List<Note> getOrderedNotes();
    void insertNote(Note note);
    void updateNote(Note updatedNote);
    void deleteNote(String id);
    int getNotesCount();

}
