package ebj.awesome.yujinnotes.model;

/**
 * Created by Yujin on 11/09/2017.
 */

public class Note {

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "desc";

    private String title;
    private String description;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
