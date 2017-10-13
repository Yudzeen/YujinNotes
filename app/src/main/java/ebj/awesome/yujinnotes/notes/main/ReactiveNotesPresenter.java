package ebj.awesome.yujinnotes.notes.main;

import java.util.List;

import ebj.awesome.yujinnotes.data.RxRepository;
import ebj.awesome.yujinnotes.model.Note;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public class ReactiveNotesPresenter implements NotesContract.Presenter {

    private NotesContract.View view;
    private RxRepository repository;
    private CompositeDisposable compositeDisposable;

    public ReactiveNotesPresenter(NotesContract.View view, RxRepository repository) {
        this.view = view;
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadNotes() {
        compositeDisposable.add(repository.getOrderedNotes()
                .subscribeWith(new DisposableSingleObserver<List<Note>>() {
                    @Override
                    public void onSuccess(@NonNull List<Note> notes) {
                        if (notes.isEmpty()) {
                            view.displayNoNotes();
                        } else {
                            view.displayNotes(notes);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.showFailedAccessingServerMessage();
                    }
                }));
    }

    @Override
    public void attemptNoteCreation() {

    }

    @Override
    public void viewNote(Note note) {

    }

    @Override
    public void addNote(Note note) {

    }

    @Override
    public void updateNote(Note note) {

    }

    @Override
    public void deleteNote(Note note) {

    }

    @Override
    public void moveNote(Note from, Note to) {

    }

    @Override
    public void updatePositions(List<Note> notes) {

    }

    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
