
package strawbericreations.com.makeupguide.userinterface;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import strawbericreations.com.makeupguide.R;
import strawbericreations.com.makeupguide.analytics.AnalyticsApplication;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity" ;
    Intent dataIntent;

    private Tracker mTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                // An example device ID
                .build();
        mAdView.loadAd(request);


        dataIntent = new Intent(this, VideoActivity.class);
    }



    public void eyesClick(View view) {
        dataIntent.putExtra("keyword", "eyemakeup");
        startActivity(dataIntent);

    }

    public void faceClick(View view) {
        dataIntent.putExtra("keyword", "facemakeup");
        startActivity(dataIntent);
    }

    public void lipsClick(View view) {
        dataIntent.putExtra("keyword", "lipsmakeup");
        startActivity(dataIntent);
    }

    public void favClick(View view){
        dataIntent.putExtra("keyword","fav");
        startActivity(dataIntent);
    }
}