package ebj.awesome.yujinnotes.data;

import java.util.List;

import ebj.awesome.yujinnotes.model.Note;

/**
 * Created by Yujin on 02/10/2017.
 */

public interface AsyncNotesRepository {

    interface LoadNotesCallback {
        void onNotesLoaded(List<Note> notes);
        void onDataNotAvailable();
    }

    interface GetNoteCallback {
        void onNoteLoaded(Note note);
        void onDataNotAvailable();
    }

    interface DeleteNoteCallback {
        void onNoteDeleted(Note note);
        void onFailure();
    }

    void getNotes(LoadNotesCallback callback);
    void getNote(String id, GetNoteCallback callback);
    void saveNote(Note note);
    void updateNote(Note note);
    void deleteNote(Note note, DeleteNoteCallback callback);

}
