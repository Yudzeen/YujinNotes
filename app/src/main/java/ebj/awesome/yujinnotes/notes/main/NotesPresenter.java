package ebj.awesome.yujinnotes.notes.main;

import java.util.List;

import ebj.awesome.yujinnotes.data.NotesRepository;
import ebj.awesome.yujinnotes.model.Note;
import ebj.awesome.yujinnotes.util.NotesHelper;

public class NotesPresenter implements NotesContract.Presenter {

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
        if (notes.isEmpty()) {
            view.displayNoNotes();
        } else {
            view.displayNotes(notes);
        }
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
        note.setPosition(notesRepository.getNotesCount());
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
    public void moveNote(Note from, Note to) {
        NotesHelper.swapPosition(from, to);
        notesRepository.updateNote(from);
        notesRepository.updateNote(to);
        view.showNoteMoved(from, to);
    }

    @Override
    public Note getNote(int position) {
        return notesRepository.getNote(position);
    }

    @Override
    public int getNotesCount() {
        return notesRepository.getNotesCount();
    }



}
