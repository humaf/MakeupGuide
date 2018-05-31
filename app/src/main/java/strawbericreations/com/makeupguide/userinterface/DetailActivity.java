package strawbericreations.com.makeupguide.userinterface;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import strawbericreations.com.makeupguide.R;
import strawbericreations.com.makeupguide.database.FavoritesContract;

import static strawbericreations.com.makeupguide.utility.Constants.API_KEY;

public class DetailActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;

    ImageView favorite;
    String vid,image,title;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.youtubeview);
        favorite =(ImageView)findViewById(R.id.fav);
        Intent in = getIntent();
        Bundle b = in.getExtras();

        if(b!=null){
            vid = (String) b.get("VideoId");
            image = (String)b.get("Image");
            title = (String)b.get("title");
        }


        youTubePlayerView.initialize("YOUR API KEY",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(vid);
                    }
                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
   }

      public void favo(View view) {
            boolean inFavorites = checkFavorites();
            if(inFavorites){
                deleteFromFavorites();
            }else{
                addToFavorites(view);
            }
            toggleFavorites();
        }



    public void addToFavorites(View view) {

        Uri uri = FavoritesContract.FavoriteEntry.CONTENT_URI;
        ContentResolver resolver = getApplicationContext().getContentResolver();
        ContentValues values = new ContentValues();
        values.clear();
        values.put(FavoritesContract.FavoriteEntry.COLUMN_ID, vid);
        values.put(FavoritesContract.FavoriteEntry.COLUMN_TITLE,title);
        values.put(FavoritesContract.FavoriteEntry.COLUMN_IMAGE, image);
        Uri check = resolver.insert(uri, values);
        Toast toast = Toast.makeText(getApplicationContext(),"Added to Favourites",Toast.LENGTH_LONG);
        toast.show();
    }

    private void deleteFromFavorites() {

        Uri uri = FavoritesContract.FavoriteEntry.CONTENT_URI;
        ContentResolver resolver = getApplicationContext().getContentResolver();

        long noDeleted = resolver.delete(uri,
                FavoritesContract.FavoriteEntry.COLUMN_ID+ " = ? ",
                new String[]{ id + "" });
        Toast toast = Toast.makeText(getApplicationContext(),"Removed from Favourites",Toast.LENGTH_LONG);
        toast.show();


    }
    /*
     * query DB to see if the movie is already there.
     * */
    private boolean checkFavorites() {
        Uri uri = FavoritesContract.FavoriteEntry.buildFavouritesUri(id);
        String[] projection = new String[]{FavoritesContract.FavoriteEntry.COLUMN_ID};
        Log.i("checking if its null",uri.toString());
        ContentResolver resolver = getApplicationContext().getContentResolver();
        Cursor cursor = null;
        try {

            cursor = resolver.query(uri, null, null, null, null);

            if (cursor.moveToFirst())
                return true;

        }
        finally {

            if(cursor != null )
                cursor.close();
        }
        return false;
    }

    private void toggleFavorites(){
        boolean inFavorites = checkFavorites();
        if(inFavorites){
            favorite.setImageResource(R.drawable.yellow_star);
        }else{
            favorite.setImageResource(R.drawable.gray);
        }
    }

}
