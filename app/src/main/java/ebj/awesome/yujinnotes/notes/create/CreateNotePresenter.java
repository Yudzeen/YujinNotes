package ebj.awesome.yujinnotes.notes.create;

import android.util.Log;

import ebj.awesome.yujinnotes.model.Note;

/**
 * Created by Yujin on 21/09/2017.
 */

public class CreateNotePresenter implements CreateNoteContract.Presenter {

    private static final String TAG = CreateNotePresenter.class.getSimpleName();

    private CreateNoteContract.View view;

    public CreateNotePresenter(CreateNoteContract.View view) {
        this.view = view;
    }

    @Override
    public void start() {
        view.setPresenter(this);
    }

    @Override
    public void onSubmit(Note note) {
        if (isTitleEmpty(note.getTitle())) {
            view.showNoTitleError();
        } else {
            view.showNoteCreated(note);
        }
    }

    private boolean isTitleEmpty(String title) {
        return title.equals("");
    }
}
