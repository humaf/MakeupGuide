package strawbericreations.com.makeupguide.userinterface;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import strawbericreations.com.makeupguide.R;

public class VideosActivity extends AppCompatActivity {

    private static final long NUMBER_OF_VIDEOS_RETURNED = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

       String searchkey = getIntent().getStringExtra("keyword");

        Bundle bundle = new Bundle();
        bundle.putString("searchkey", searchkey);

        VideoFragment fragobj = new VideoFragment();
        fragobj.setArguments(bundle);

    }

    public Boolean isNetworkAvailable(Context context) {

        Boolean resultValue = false; // Initial Value

        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            resultValue = true;
        }
        return resultValue;
    }
}