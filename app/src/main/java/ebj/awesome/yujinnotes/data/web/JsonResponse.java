package ebj.awesome.yujinnotes.data.web;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ebj.awesome.yujinnotes.model.Note;

/**
 * Created by Yujin on 02/10/2017.
 */

public class JsonResponse {

    @SerializedName("notes")
    @Expose
    private List<Note> notes;

    @SerializedName("note")
    @Expose
    private List<Note> note;

    @SerializedName("message")
    private String message;

    public List<Note> getNotes() {
        return notes;
    }

    public List<Note> getNote() {
        return note;
    }

    public String getMessage() {
        return message;
    }
}
