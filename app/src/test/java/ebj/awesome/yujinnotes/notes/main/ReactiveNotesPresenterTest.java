package ebj.awesome.yujinnotes.notes.main;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ebj.awesome.yujinnotes.data.NotesRepository;
import ebj.awesome.yujinnotes.data.RxRepository;
import ebj.awesome.yujinnotes.model.Note;
import io.reactivex.Single;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ReactiveNotesPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    RxRepository notesRepository;

    @Mock
    NotesContract.View view;

    ReactiveNotesPresenter presenter;

    final List<Note> MANY_NOTES = Arrays.asList(new Note("Yujin"), new Note("Yudzeen"), new Note("Eugene"));

    @Before
    public void setUp() throws Exception {
        presenter = new ReactiveNotesPresenter(view, notesRepository);
        presenter.start();
    }

    @Test
    public void shouldPassNotesToView() {
        when(notesRepository.getOrderedNotes()).thenReturn(Single.just(MANY_NOTES));

        presenter.loadNotes();

        verify(view).displayNotes(MANY_NOTES);
    }

    @Test
    public void shouldHandleEmptyNotesList() {
        when(notesRepository.getOrderedNotes()).thenReturn(Single.<List<Note>>just(new ArrayList<Note>()));

        presenter.loadNotes();

        verify(view).displayNoNotes();
    }

    @Test
    public void shouldHandleDatabaseException() {
        when(notesRepository.getOrderedNotes()).thenReturn(Single.<List<Note>>error(new Throwable("error")));

        presenter.loadNotes();

        verify(view).showFailedAccessingServerMessage();
    }

}