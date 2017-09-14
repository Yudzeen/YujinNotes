package ebj.awesome.yujinnotes.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yujin on 11/09/2017.
 */

public class Note implements Parcelable {

    public static final String TAG = Note.class.getName();

    public static int ID_COUNT = 0;

    private int id;
    private String title;
    private String description;
    private boolean deleted;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.id = ID_COUNT;
        this.deleted = false;
        ID_COUNT++;
    }

    public Note(int id, String title, String description, boolean deleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.deleted = deleted;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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
        deleted = source.readByte() == 1;
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
        dest.writeByte((byte) (deleted ? 1 : 0));
    }


}
