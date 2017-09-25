package ebj.awesome.yujinnotes.notes;

import java.util.ArrayList;
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
        notesRepository.insertNote(note);
        view.showNoteCreated(note);
        view.showNoteCreatedMessage();
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

}
