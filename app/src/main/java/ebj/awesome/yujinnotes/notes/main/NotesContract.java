package ebj.awesome.yujinnotes.notes.main;

import java.util.List;

import ebj.awesome.yujinnotes.archframework.BasePresenter;
import ebj.awesome.yujinnotes.archframework.BaseView;
import ebj.awesome.yujinnotes.model.Note;

/**
 * Created by Yujin on 21/09/2017.
 */

public interface NotesContract {

    interface View extends BaseView<Presenter> {

        void displayNotes(List<Note> notes);
        void displayNoNotes();
        void displayNoteDetails(Note note);
        void showCreateNoteView();
        void showNoteCreated(Note note);
        void showNoteUpdated(Note note);
        void showNoteDeleted(Note note);
        void showNoteCreatedMessage();
        void showNoteDeletedMessage();
        void showNoteMoved(Note from, Note to);
        void showProgressIndicator();
        void hideProgressIndicator();
        void showFailedAccessingServerMessage();

    }

    interface Presenter extends BasePresenter {

        void loadNotes();
        void attemptNoteCreation();
        void viewNote(Note note);
        void addNote(Note note);
        void updateNote(Note note);
        void deleteNote(Note note);
        void moveNote(Note from, Note to);

        // update all positions (used after deletion)
        void updatePositions(List<Note> notes);

    }

}
