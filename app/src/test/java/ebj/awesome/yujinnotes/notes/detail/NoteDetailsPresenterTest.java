package ebj.awesome.yujinnotes.notes.detail;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

/**
 * Created by Yujin on 25/09/2017.
 */
public class NoteDetailsPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    NoteDetailsContract.View view;

    NoteDetailsPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new NoteDetailsPresenter(view);
        presenter.start();
    }

    @Test
    public void presenterIsSet() {
        verify(view).setPresenter(presenter);
    }

    @Test
    public void onEdit_displayEditableFields() {
        presenter.onEdit();

        verify(view).setEditable();
    }

    @Test
    public void onDelete_displayDeleteConfirmation() {
        presenter.onAttemptTrash();

        verify(view).displayTrashConfirmation();
    }

    @Test
    public void onConfirmDelete_removeNote() {
        presenter.onConfirmTrash();


    }

}