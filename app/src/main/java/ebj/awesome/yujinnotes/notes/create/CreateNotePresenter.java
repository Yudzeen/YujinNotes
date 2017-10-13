package ebj.awesome.yujinnotes.notes.create;

import ebj.awesome.yujinnotes.model.Note;
import ebj.awesome.yujinnotes.model.NoteBuilder;

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
    public void onSubmit(String title, String description) {
        if (isTitleValid(title)) {
            Note note = new NoteBuilder()
                    .setTitle(title)
                    .addDescription(description)
                    .build();
            view.showNoteCreated(note);
        } else {
            view.showNoTitleError();
        }
    }

    private boolean isTitleValid(String title) {
        return !title.equals("");
    }
}
