package ebj.awesome.yujinnotes.notes.create;

import ebj.awesome.yujinnotes.archframework.BasePresenter;
import ebj.awesome.yujinnotes.archframework.BaseView;
import ebj.awesome.yujinnotes.model.Note;

/**
 * Created by Yujin on 21/09/2017.
 */

public interface CreateNoteContract {

    interface View extends BaseView<Presenter> {

        void showNoTitleError();
        void showNoteCreated(Note note);

    }

    interface Presenter extends BasePresenter {

        void onSubmit(String title, String description);

    }
}
