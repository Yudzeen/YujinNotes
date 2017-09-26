package ebj.awesome.yujinnotes.notes.create;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import ebj.awesome.yujinnotes.model.Note;

import static org.mockito.Mockito.*;

/**
 * Created by Yujin on 21/09/2017.
 */
public class CreateNotePresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    CreateNoteContract.View view;

    CreateNotePresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new CreateNotePresenter(view);
        presenter.start();
    }

    @Test
    public void presenterIsSet() {
        verify(view).setPresenter(presenter);
    }

    @Test
    public void handleEmptyTitle() {
        presenter.onSubmit(new Note("", "Description"));

        verify(view).showNoTitleError();
    }

    @Test
    public void onSubmit_validNote() {
        Note validNote = new Note("Yujin");

        presenter.onSubmit(validNote);

        verify(view).showNoteCreated(validNote);
    }
}