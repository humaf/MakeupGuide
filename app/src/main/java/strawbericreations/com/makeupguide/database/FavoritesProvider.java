package strawbericreations.com.makeupguide.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import static strawbericreations.com.makeupguide.database.FavoritesContract.CONTENT_AUTHORITY;

/**
 * Created by redrose on 5/19/18.
 */

public class FavoritesProvider extends ContentProvider {

    static final String TAG = FavoritesProvider.class.getSimpleName();

    //To query content provider, we should specify the query string in the form of a URI of the below format
    // <prefix>://<authority>/<data_type>/<id>
    // <prefix> is always set to content://
    // authority specifies the name of the content provider. For third party content providers this should be fully qualified name
    // data_type indicates the particular data the provider provides.

    static final String PROVIDER_NAME = "strawbericreations.com.makeupguide.database.FavoritesProvider";
    static final String URL = "content://" + PROVIDER_NAME + "/favourites";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final int FAVORITES = 1;
    static final int FAVORITES_ID = 2;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FavoritesDBHelper mOpenHelper;
    private SQLiteDatabase favDB;
    boolean thrown = true;

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority,FavoritesContract.PATH_VIDEOS, FAVORITES);
        matcher.addURI(authority, FavoritesContract.PATH_VIDEOS + "/#", FAVORITES_ID);

        return matcher;
    }
    @Override
    public boolean onCreate() {
        Log.e(TAG,"FavoritesProvider onCreate called" );
        Context context = getContext();
        FavoritesDBHelper dbHelper = new FavoritesDBHelper(context);
        favDB = dbHelper.getWritableDatabase();
        return (favDB == null) ? false : true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Log.i("Content",CONTENT_URI.toString());
        final int match = sUriMatcher.match(uri);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (match) {
            case FAVORITES:
                qb.setTables(FavoritesContract.FavoriteEntry.TABLE_FAVOURITES);
                Log.e(TAG,"query uriMatcher favorites");
                break;
            case FAVORITES_ID: {
                qb.setTables(FavoritesContract.FavoriteEntry.TABLE_FAVOURITES);
                Log.e(TAG, "query uriMatcher FAVORITES_ID");
                qb.appendWhere(FavoritesContract.FavoriteEntry.COLUMN_ID + "=" +
                        uri.getLastPathSegment());
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        Cursor cursor = qb.query (
                favDB,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
Log.i("Cursor empty or not",cursor.toString());
     //   cursor.setNotificationUri(getContext().getContentResolver(),CONTENT_URI);
      //  cursor.setNotificationUri(getContext().getContentResolver(),Uri.parse("content://" + CONTENT_AUTHORITY));
        //   getContext().getContentResolver().notifyChange(CONTENT_URI,null);
       getContext().getContentResolver().notifyChange(Uri.parse("content://" + CONTENT_AUTHORITY), null,false);
    //    getContext().getContentResolver().notifyChange()
       return cursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,  ContentValues values) {
        long rowID = favDB.insert(FavoritesContract.FavoriteEntry.TABLE_FAVOURITES, "", values);
        Log.e(TAG,"FavoritesProvider insert rowID:"+rowID);

        /**
         * If record is added successfully
         */
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
           // getContext().getContentResolver().notifyChange(_uri, null);
            getContext().getContentResolver().notifyChange(Uri.parse("content://" + CONTENT_AUTHORITY), null);
            return _uri;
        }
        try {
            throw new SQLException("Failed to add new record into "+uri);
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection,  String[] selectionArgs) {
        final SQLiteDatabase db = favDB;
        int count = 0;

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case FAVORITES:
                count = favDB.delete(FavoritesContract.FavoriteEntry.TABLE_FAVOURITES, selection, selectionArgs);
                break;
            case FAVORITES_ID:

                String id = uri.getPathSegments().get(1);
                count = favDB.delete(FavoritesContract.FavoriteEntry.TABLE_FAVOURITES,FavoritesContract.FavoriteEntry.COLUMN_ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri,  ContentValues values, String selection,  String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated = 0;

        switch (match) {
            case FAVORITES:
                rowsUpdated = db.update(
                        FavoritesContract.FavoriteEntry.TABLE_FAVOURITES,
                        values,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
