package ebj.awesome.yujinnotes.data.local;

import java.util.List;
import java.util.concurrent.Callable;

import ebj.awesome.yujinnotes.data.NotesRepository;
import ebj.awesome.yujinnotes.data.RxRepository;
import ebj.awesome.yujinnotes.model.Note;
import io.reactivex.Single;


public class ReactiveRepository implements RxRepository {

    private DatabaseHelper db;

    public ReactiveRepository(DatabaseHelper db) {
        this.db = db;
    }

    @Override
    public Single<List<Note>> getOrderedNotes() {
        return Single.fromCallable(new Callable<List<Note>>() {
            @Override
            public List<Note> call() throws Exception {
                return db.getOrderedNotes();
            }
        });
    }
}
