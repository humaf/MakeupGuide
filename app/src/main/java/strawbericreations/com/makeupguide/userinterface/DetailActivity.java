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

import static android.view.View.VISIBLE;
import static strawbericreations.com.makeupguide.utility.Constants.API_KEY;

public class DetailActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;

    ImageView favorite;
    String vid,image,title;
    boolean isFav;


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
            title = (String)b.get("Title");


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
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
   }



      public void delete(View view){
        deleteFromFavorites(vid);
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
        Toast toast = Toast.makeText(getApplicationContext(),R.string.AddingFavs,Toast.LENGTH_LONG);
        toast.show();
        isFav = true;
        toggleFavorites();


    }

    public void deleteFromFavorites(String ids) {

        Uri uri = FavoritesContract.FavoriteEntry.CONTENT_URI;
        ContentResolver resolver = getApplicationContext().getContentResolver();
        long noDeleted = resolver.delete(uri,
                FavoritesContract.FavoriteEntry.COLUMN_ID+ " = ? ",
                new String[]{ ids + "" });
        Toast toast = Toast.makeText(getApplicationContext(),getString(R.string.DeletingFavs),Toast.LENGTH_LONG);
        toast.show();
        isFav = false;
toggleFavorites();

    }

    private void toggleFavorites(){
       // boolean inFavorites = checkFavorites();
        if(isFav){
            favorite.setImageResource(R.drawable.yellow_star);
        }else{
            favorite.setImageResource(R.drawable.gray);
        }
    }

}
