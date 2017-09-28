package ebj.awesome.yujinnotes.util;

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

}
