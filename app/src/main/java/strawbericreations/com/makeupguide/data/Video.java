package strawbericreations.com.makeupguide.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by redrose on 4/26/18.
 */

public class Video implements Parcelable {

    private String id;
    private String thumbnailURL;

    private String title;
    private String description;
    private Video mInfo;

    public Video() {}
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(thumbnailURL);

        dest.writeString(title);
        dest.writeString(description);

        dest.writeParcelable(mInfo,flags);
    }

    private void readFromParcel(Parcel in) {
        id = in.readString();
        thumbnailURL = in.readString();

        title = in.readString();
        description = in.readString();

        mInfo = in.readParcelable(Video.class.getClassLoader());
    }

    public static final Parcelable.Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            Video list = new Video();
            list.id = source.readString();
            list.thumbnailURL = source.readString();

            list.title = source.readString();
            list.description = source.readString();

            return list;
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };


}
