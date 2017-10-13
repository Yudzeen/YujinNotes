package ebj.awesome.yujinnotes.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class NoteBuilderTest {

    @Test
    public void shouldBuildNotesProperly() {
        String title = "TITLE";
        Note note = new Note(title);

        Note noteBuilt = new NoteBuilder()
                .setTitle(title)
                .build();

        assertEquals(note.getTitle(), noteBuilt.getTitle());
    }

    @Test
    public void shouldBuildNotesProperlyWithDescription() {
        String title = "TITLE";
        String description = "DESCRIPTION";
        Note note = new Note(title, description);

        Note noteBuilt = new NoteBuilder()
                .setTitle(title)
                .addDescription(description)
                .build();

        assertEquals(note.getTitle(), noteBuilt.getTitle());
        assertEquals(note.getDescription(), noteBuilt.getDescription());
    }

}