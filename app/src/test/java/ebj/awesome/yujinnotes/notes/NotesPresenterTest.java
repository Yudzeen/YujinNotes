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
import ebj.awesome.yujinnotes.notes.main.NotesContract;
import ebj.awesome.yujinnotes.notes.main.NotesPresenter;

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

    @Test
    public void addNote_InsertInRepo() {
        Note note = new Note("Title", "Description");

        presenter.addNote(note);

        verify(notesRepository).insertNote(note);
    }

    @Test
    public void addNote_showNoteCreated() {
        Note note = new Note("Title", "Description");

        presenter.addNote(note);

        verify(view).showNoteCreated(note);
    }

    @Test
    public void addNote_showNoteCreatedMessage() {
        Note note = new Note("Title", "Description");

        presenter.addNote(note);

        verify(view).showNoteCreatedMessage();
    }

    @Test
    public void updateNote_updateInRepo() {
        Note note = new Note("Title", "Description");

        presenter.updateNote(note);

        verify(notesRepository).updateNote(note);
    }

    @Test
    public void deleteNote_deleteInRepoAndShowDeletedMessage() {
        Note note = new Note("Title");

        presenter.deleteNote(note);

        verify(notesRepository).deleteNote(note.getId());
        verify(view).showNoteDeletedMessage();
        verify(view).showNoteDeleted(note);
    }




}