package strawbericreations.com.makeupguide.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by redrose on 5/19/18.
 */

public class FavoritesContract implements BaseColumns{

    private FavoritesContract() {

    }

    public static final String PATH_VIDEOS = "videos";

    public static final String CONTENT_AUTHORITY="strawbericreations.com.makeupguide";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static abstract class FavoriteEntry implements BaseColumns{
        // table name
        public static final String TABLE_FAVOURITES = "favourites";
        // columns
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";

        public static final String PATH_VIDEOS = "videos";

        // create content uri
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_VIDEOS).build();
        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_FAVOURITES;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_FAVOURITES;

        // for building URIs on insertion
       public static Uri buildFavouritesUri(int id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] PROJECTION_ALL =
                {COLUMN_ID, COLUMN_IMAGE, COLUMN_TITLE};

    }
}
