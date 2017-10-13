package ebj.awesome.yujinnotes.model;

public class NoteBuilder {

    private String title;
    private String description;

    public NoteBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public NoteBuilder addDescription(String description) {
        this.description = description;
        return this;
    }

    public Note build() {
        return new Note(title, description);
    }

}
