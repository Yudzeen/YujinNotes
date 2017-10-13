package ebj.awesome.yujinnotes.data;

import java.util.List;

import ebj.awesome.yujinnotes.model.Note;
import io.reactivex.Single;

public interface RxRepository {

    Single<List<Note>> getOrderedNotes();

}
