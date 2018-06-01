package strawbericreations.com.makeupguide.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by redrose on 5/19/18.
 */

public class FavoritesDBHelper extends SQLiteOpenHelper{

    public static final String LOG_TAG = FavoritesDBHelper.class.getSimpleName();

    //name & version
    private static final String DATABASE_NAME = "favourites.db";
    private static final int DATABASE_VERSION = 13;

    public FavoritesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAV0RITE_VIDEO_TABLE = "CREATE TABLE " +
                FavoritesContract.FavoriteEntry.TABLE_FAVOURITES+ "(" + FavoritesContract.FavoriteEntry.COLUMN_ID +
                " TEXT PRIMARY KEY NOT NULL, " +
                FavoritesContract.FavoriteEntry.COLUMN_TITLE + " TEXT, " +
                FavoritesContract.FavoriteEntry.COLUMN_IMAGE +
                " TEXT );" ;

        db.execSQL(SQL_CREATE_FAV0RITE_VIDEO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to " +
                newVersion + ". OLD DATA WILL BE DESTROYED");
        // Drop the table
        db.execSQL("DROP TABLE IF EXISTS " + FavoritesContract.FavoriteEntry.TABLE_FAVOURITES);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                FavoritesContract.FavoriteEntry.TABLE_FAVOURITES+ "'");
        // re-create database
        onCreate(db);

    }
}
