package ebj.awesome.yujinnotes.notes.main;

import android.util.Log;

import java.util.List;

import ebj.awesome.yujinnotes.data.NotesRepository;
import ebj.awesome.yujinnotes.model.Note;

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
        List<Note> notes = notesRepository.getOrderedNotes();
        Log.i(TAG, notes.size() + " notes received.");
        Log.i(TAG, notes.toString());
        view.displayNotes(notes);
    }

    @Override
    public void attemptNoteCreation() {
        view.showCreateNoteView();
    }

    @Override
    public void viewNote(Note note) {
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
        view.showNoteUpdated(note);
    }

    @Override
    public void deleteNote(Note note) {
        notesRepository.deleteNote(note.getId());
        view.showNoteDeletedMessage();
        view.showNoteDeleted(note);
    }

    @Override
    public void updateNotePositions(List<Note> notes) {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            notesRepository.updateNote(note);
        }
    }

    @Override
    public int getNotesCount() {
        return notesRepository.getNotesCount();
    }

}
