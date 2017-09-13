package ebj.awesome.yujinnotes.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yujin on 11/09/2017.
 */

public class Note implements Parcelable {

    public static final String TAG = Note.class.getName();

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "desc";

    public static int ID_COUNT = 0;

    private int id;
    private String title;
    private String description;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.id = ID_COUNT;
        ID_COUNT++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        id = source.readInt();
        title = source.readString();
        description = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
    }


}
