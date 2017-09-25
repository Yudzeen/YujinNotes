package ebj.awesome.yujinnotes.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ebj.awesome.yujinnotes.model.Note;

import static org.junit.Assert.*;

/**
 * Created by Yujin on 24/09/2017.
 */
public class NotesHelperTest {

    public final List<Note> EMPTY_LIST = new ArrayList<>();
    public final List<Note> MANY_NOTES = Arrays.asList(new Note("Yujin"), new Note("Eugene"), new Note("Yudzeen"));

    @Test
    public void indexOf_emptyList() {
        Note note = new Note("Title");

        int indexOf = NotesHelper.indexOf(note, EMPTY_LIST);

        assertEquals(indexOf, -1);
    }

    @Test
    public void indexOf_notInList() {
        Note note = new Note("Title");

        int indexOf = NotesHelper.indexOf(note, MANY_NOTES);

        assertEquals(indexOf, -1);
    }

    @Test
    public void indexOf_returnIndex() {
        Note note = MANY_NOTES.get(0);

        int indexOf = NotesHelper.indexOf(note, MANY_NOTES);

        assertEquals(indexOf, 0);
    }

    @Test
    public void indexOf_listTest() {
        for (int i = 0; i < MANY_NOTES.size(); i++) {
            Note note = MANY_NOTES.get(i);

            int indexOf = NotesHelper.indexOf(note, MANY_NOTES);

            assertEquals(indexOf, i);
        }
    }

}