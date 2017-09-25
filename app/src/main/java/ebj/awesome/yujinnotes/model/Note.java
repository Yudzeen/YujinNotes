package ebj.awesome.yujinnotes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

public class Note implements Parcelable {

    public static final String TAG = Note.class.getSimpleName();

    private String id;
    private String title;
    private String description;
    private boolean trashed;

    public Note(String title) {
        this(UUID.randomUUID().toString(), title, "", false);
    }

    public Note(String title, String description) {
        this(UUID.randomUUID().toString(), title, description, false);
    }

    public Note(String id, String title, String description, boolean trashed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.trashed = trashed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public boolean isTrashed() {
        return trashed;
    }

    public void setTrashed(boolean trashed) {
        this.trashed = trashed;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Title: " + title;
    }

    /**Parcel**/

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public Note(Parcel source) {
        id = source.readString();
        title = source.readString();
        description = source.readString();
        trashed = source.readByte() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte((byte) (trashed ? 1 : 0));
    }


}
