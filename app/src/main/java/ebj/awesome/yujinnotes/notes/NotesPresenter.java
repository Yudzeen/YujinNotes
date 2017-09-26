package ebj.awesome.yujinnotes.notes;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ebj.awesome.yujinnotes.data.NotesRepository;
import ebj.awesome.yujinnotes.model.Note;

/**
 * Created by Yujin on 20/09/2017.
 */

public class NotesPresenter implements NotesContract.Presenter {

    private static final String TAG = NotesPresenter.class.getSimpleName();

    private NotesContract.View view;
    private NotesRepository notesRepository;

    public NotesPresenter(NotesContract.View view, NotesRepository notesRepository) {
        this.view = view;
        this.notesRepository = notesRepository;
    }

    @Override
    public void start() {
        view.setPresenter(this);
    }

    @Override
    public void loadNotes() {
        List<Note> notes = notesRepository.getNotes();
        List<Note> unTrashedNotes = new ArrayList<>();
        for (Note note : notes) {
            if (!note.isTrashed()) {
                unTrashedNotes.add(note);
            }
        }
        sortByPosition(unTrashedNotes);
        Log.i(TAG, unTrashedNotes.toString());
        view.displayNotes(unTrashedNotes);
    }

    @Override
    public void attemptNoteCreation() {
        view.showCreateNoteView();
    }

    @Override
    public void onViewNote(Note note) {
        view.displayNoteDetails(note);
    }

    @Override
    public void addNote(Note note) {
        view.showNoteCreated(note);
        view.showNoteCreatedMessage();
        notesRepository.insertNote(note);
    }

    @Override
    public void updateNote(Note note) {
        notesRepository.updateNote(note);
        if (note.isTrashed()) {
            trashNote(note);
        } else {
            view.showNoteUpdated(note);
        }
    }

    @Override
    public void trashNote(Note note) {
        view.showNoteTrashed(note);
        view.showNoteTrashedMessage();
    }

    @Override
    public void updateNotePositions(List<Note> notes) {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            notesRepository.updateNote(note);
        }
    }

    private void sortByPosition(List<Note> notes) {
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {
                if (note1.getPosition() < note2.getPosition()) {
                    return -1;
                }
                return 1;
            }
        });
    }
}
