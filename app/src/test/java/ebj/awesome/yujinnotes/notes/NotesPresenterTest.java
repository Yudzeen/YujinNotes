package ebj.awesome.yujinnotes.notes;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;

import ebj.awesome.yujinnotes.data.NotesRepository;
import ebj.awesome.yujinnotes.model.Note;

import static org.mockito.Mockito.*;

/**
 * Created by Yujin on 21/09/2017.
 */
public class NotesPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    NotesRepository notesRepository;

    @Mock
    NotesContract.View view;

    NotesPresenter presenter;

    final List<Note> MANY_NOTES = Arrays.asList(new Note("Yujin"), new Note("Yudzeen"), new Note("Eugene"));

    @Before
    public void setUp() throws Exception {
        presenter = new NotesPresenter(view, notesRepository);
        presenter.start();
    }

    @Test
    public void presenterIsSet() {
        verify(view).setPresenter(presenter);
    }

    @Test
    public void shouldPassNotesToView() {
        when(notesRepository.getNotes()).thenReturn(MANY_NOTES);

        presenter.loadNotes();

        verify(view).displayNotes(MANY_NOTES);
    }

    @Test
    public void shouldShowNoteCreationSuccessMessage() {

        presenter.addNote(new Note("Title", "Description"));

        verify(view).showNoteCreatedMessage();
    }

    @Test
    public void onAttemptNoteCreation_showCreateNoteView() {
        presenter.attemptNoteCreation();

        verify(view).showCreateNoteView();
    }

    @Test
    public void viewNote_showsNoteDetails() {
        Note note = new Note("Title");

        presenter.onViewNote(note);

        verify(view).displayNoteDetails(note);
    }




}