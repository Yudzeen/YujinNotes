package ebj.awesome.yujinnotes.notes.detail;

import ebj.awesome.yujinnotes.data.NotesRepository;
import ebj.awesome.yujinnotes.model.Note;

/**
 * Created by Yujin on 24/09/2017.
 */

public class NoteDetailsPresenter implements NoteDetailsContract.Presenter {

    private NoteDetailsContract.View view;

    public NoteDetailsPresenter(NoteDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.setPresenter(this);
    }

    @Override
    public void onEdit() {
        view.setEditable(true);
    }

    @Override
    public void onEditDone() {
        view.setEditable(false);
        view.showNoteUpdated();
    }

    @Override
    public void onAttemptTrash() {
        view.displayDeleteConfirmation();
    }

    @Override
    public void onConfirmTrash() {
        view.showNoteDeleted();
    }
}
