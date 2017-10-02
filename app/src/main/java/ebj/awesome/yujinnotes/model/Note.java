package ebj.awesome.yujinnotes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Note implements Parcelable {

    public static final String TAG = Note.class.getSimpleName();

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("position")
    @Expose
    private int position;

    public Note(String title) {
        this(UUID.randomUUID().toString(), title, "", -1);
    }

    public Note(String title, String description) {
        this(UUID.randomUUID().toString(), title, description, -1);
    }

    public Note(String id, String title, String description, int position) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.position = position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ID: " + id + " Title: " + title + " Pos: " + position;
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
        position = source.readInt();
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
        dest.writeInt(position);
    }


}
