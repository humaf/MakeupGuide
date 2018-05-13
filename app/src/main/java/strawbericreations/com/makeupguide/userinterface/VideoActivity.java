package strawbericreations.com.makeupguide.userinterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import strawbericreations.com.makeupguide.R;
import android.content.Context;
import android.util.Log;

public class VideoActivity extends AppCompatActivity {

    private static final long NUMBER_OF_VIDEOS_RETURNED = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

    //    String searchkey = getIntent().getStringExtra("keyword");

      //  Bundle bundle = new Bundle();
    //    bundle.putString("searchkey", searchkey);

      //  VideoFragment fragobj = new VideoFragment();
       // fragobj.setArguments(bundle);
        Log.i("Video Activity","Checking");
    }


/*    public Boolean isNetworkAvailable(Context context) {

        Boolean resultValue = false; // Initial Value

        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            resultValue = true;
        }
        return resultValue;
    }
    */
}
