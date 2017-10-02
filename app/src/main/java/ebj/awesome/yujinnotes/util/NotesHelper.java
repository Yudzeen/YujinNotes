package ebj.awesome.yujinnotes.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ebj.awesome.yujinnotes.model.Note;

public class NotesHelper {

    public static int indexOf(Note note, List<Note> list) {
        int index = -1;
        String id = note.getId();
        for (int i = 0; i < list.size(); i++) {
            if (id.equals(list.get(i).getId())) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static void swapPosition(Note from, Note to) {
        int temp = from.getPosition();
        from.setPosition(to.getPosition());
        to.setPosition(temp);
    }

    public static void sortByPosition(List<Note> notes) {
        Collections.sort(notes, new Comparator<Note>() {
            @Override
            public int compare(Note n1, Note n2) {
                if (n1.getPosition() < n2.getPosition()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
    }

    public static void updatePositions(List<Note> notes) {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            note.setPosition(i);
        }
    }

}
