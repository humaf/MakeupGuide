package strawbericreations.com.makeupguide.userinterface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import strawbericreations.com.makeupguide.R;

import static strawbericreations.com.makeupguide.utility.Constants.API_KEY;

public class DetailActivity extends YouTubeBaseActivity {

    YouTubePlayerView youTubePlayerView;
    Button play;
    Button fav;

    String vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.youtubeview);

        fav =(Button)findViewById(R.id.favbtn);

        Intent in = getIntent();

        Bundle b = in.getExtras();

        if(b!=null){

            vid = (String) b.get("VideoId");
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
}
