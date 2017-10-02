package ebj.awesome.yujinnotes.notes.main;

import java.util.List;

import ebj.awesome.yujinnotes.data.AsyncNotesRepository;
import ebj.awesome.yujinnotes.model.Note;
import ebj.awesome.yujinnotes.util.NotesHelper;

/**
 * Created by Yujin on 02/10/2017.
 */

public class AsyncNotesPresenter implements NotesContract.Presenter {

    private NotesContract.View view;
    private AsyncNotesRepository repository;

    public AsyncNotesPresenter(NotesContract.View view, AsyncNotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void start() {
        view.setPresenter(this);
    }

    @Override
    public void loadNotes() {
        repository.getNotes(new AsyncNotesRepository.LoadNotesCallback() {
            @Override
            public void onNotesLoaded(List<Note> notes) {
                if (notes.isEmpty()) {
                    view.displayNoNotes();
                } else {
                    NotesHelper.sortByPosition(notes);
                    view.displayNotes(notes);
                }
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
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
    public void addNote(final Note note) {
        repository.saveNote(note);
        view.showNoteCreated(note);
        view.showNoteCreatedMessage();
    }

    @Override
    public void updateNote(Note note) {
        repository.updateNote(note);
        view.showNoteUpdated(note);
    }

    @Override
    public void deleteNote(Note note) {
        repository.deleteNote(note, new AsyncNotesRepository.DeleteNoteCallback() {

            @Override
            public void onNoteDeleted(Note note) {
                view.showNoteDeleted(note);
                view.showNoteDeletedMessage();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void moveNote(Note from, Note to) {
        NotesHelper.swapPosition(from, to);
        repository.updateNote(from);
        repository.updateNote(to);
        view.showNoteMoved(from, to);
    }

    @Override
    public void updatePositions(List<Note> notes) {
        NotesHelper.updatePositions(notes);
        for (Note note: notes) {
            repository.updateNote(note);
        }
    }

}
