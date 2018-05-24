package strawbericreations.com.makeupguide.model;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by redrose on 5/7/18.
 */

public class Video implements Parcelable{

    private  String id;
    private   String thumbnailURL;
    private String title;

  /*  public Video(String title, String imageUrl, String videoId) {
        this.title = title;
        this.thumbnailURL = imageUrl ;
        this.id = videoId;
    }*/

  public Video(){
      super();
  }
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
 @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(thumbnailURL);

        dest.writeString(title);

    }

    private void readFromParcel(Parcel in) {
        id = in.readString();
        thumbnailURL = in.readString();

        title = in.readString();

    }

    public static final Parcelable.Creator<Video> CREATOR = new Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
         //   Video list = new Video(title, thumbnailURL, id);
            Video list = new Video();
            list.id = source.readString();
            list.thumbnailURL = source.readString();

            list.title = source.readString();


            return list;
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
