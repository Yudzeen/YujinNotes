package ebj.awesome.yujinnotes.notes.main;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.List;

import ebj.awesome.yujinnotes.data.AsyncNotesRepository;
import ebj.awesome.yujinnotes.model.Note;

import static ebj.awesome.yujinnotes.data.AsyncNotesRepository.DeleteNoteCallback;
import static ebj.awesome.yujinnotes.data.AsyncNotesRepository.LoadNotesCallback;
import static java.util.Collections.EMPTY_LIST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

public class AsyncNotesPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    AsyncNotesRepository notesRepository;

    @Mock
    NotesContract.View view;

    AsyncNotesPresenter presenter;

    final List<Note> MANY_NOTES = Arrays.asList(new Note("Yujin"), new Note("Yudzeen"), new Note("Eugene"));

    final Note SAMPLE_NOTE = new Note("Note Title");

    @Before
    public void setUp() throws Exception {
        presenter = new AsyncNotesPresenter(view, notesRepository);
        presenter.start();
    }

    @Test
    public void onLoadNotes_retrieveAndDisplayNotes() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((LoadNotesCallback) invocation.getArguments()[0]).onNotesLoaded(MANY_NOTES);
                return null;
            }
        }).when(notesRepository).getNotes(any(LoadNotesCallback.class));

        presenter.loadNotes();

        verify(view).showProgressIndicator();
        verify(view).hideProgressIndicator();
        verify(view).displayNotes(MANY_NOTES);
    }

    @Test
    public void onLoadNotes_handleEmptyNoteList() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((LoadNotesCallback) invocation.getArguments()[0]).onNotesLoaded(EMPTY_LIST);
                return null;
            }
        }).when(notesRepository).getNotes(any(LoadNotesCallback.class));

        presenter.loadNotes();

        verify(view).showProgressIndicator();
        verify(view).hideProgressIndicator();
        verify(view).displayNoNotes();
    }

    @Test
    public void onLoadNotes_handleErrorAccessingRemoteServer() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((LoadNotesCallback) invocation.getArguments()[0]).onDataNotAvailable();
                return null;
            }
        }).when(notesRepository).getNotes(any(LoadNotesCallback.class));

        presenter.loadNotes();

        verify(view).showProgressIndicator();
        verify(view).hideProgressIndicator();
        verify(view).showFailedAccessingServerMessage();
    }

    @Test
    public void attemptNoteCreation_showCreateNoteView() {
        presenter.attemptNoteCreation();

        verify(view).showCreateNoteView();
    }

    @Test
    public void viewNote_showNoteView() {
        presenter.viewNote(SAMPLE_NOTE);

        verify(view).displayNoteDetails(SAMPLE_NOTE);
    }

    @Test
    public void updateNote_updateRepoAndShowNoteUpdated() {
        presenter.updateNote(SAMPLE_NOTE);

        verify(view).showNoteUpdated(SAMPLE_NOTE);
    }

    @Test
    public void deleteNote_repoAndShowNoteDeleted() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((DeleteNoteCallback) invocation.getArguments()[1]).onNoteDeleted(SAMPLE_NOTE);
                return null;
            }
        }).when(notesRepository).deleteNote(eq(SAMPLE_NOTE), any(DeleteNoteCallback.class));

        presenter.deleteNote(SAMPLE_NOTE);

        verify(view).showNoteDeleted(SAMPLE_NOTE);
        verify(view).showNoteDeletedMessage();
    }

    @Test
    public void deleteNote_handleDeleteError() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((DeleteNoteCallback) invocation.getArguments()[1]).onFailure();
                return null;
            }
        }).when(notesRepository).deleteNote(eq(SAMPLE_NOTE), any(DeleteNoteCallback.class));

        presenter.deleteNote(SAMPLE_NOTE);

        verify(view).showProgressIndicator();
        verify(view).hideProgressIndicator();
        verify(view).showFailedAccessingServerMessage();
    }


}