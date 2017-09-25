package ebj.awesome.yujinnotes.notes.detail;

import ebj.awesome.yujinnotes.archframework.BasePresenter;
import ebj.awesome.yujinnotes.archframework.BaseView;
import ebj.awesome.yujinnotes.model.Note;

/**
 * Created by Yujin on 24/09/2017.
 */

public interface NoteDetailsContract {

    interface View extends BaseView<Presenter> {

        void setEditable(boolean editable);

        void showNoteUpdated();

        void displayTrashConfirmation();

        void showNoteTrashed();

    }

    interface Presenter extends BasePresenter {

        void onEdit();

        void onEditDone();

        void onAttemptTrash();

        void onConfirmTrash();

    }

}
