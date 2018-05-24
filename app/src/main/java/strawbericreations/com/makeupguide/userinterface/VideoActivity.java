package strawbericreations.com.makeupguide.userinterface;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import strawbericreations.com.makeupguide.R;
import strawbericreations.com.makeupguide.utility.Constants;

import android.util.Log;
import android.view.MenuItem;

public class VideoActivity extends AppCompatActivity {

    public static String url;

   //  public static String Favorite;
    public static boolean Favorite;

    public static String url1= Constants.API_URL_FACE;

    public  static String url2 =Constants.API_URL_LIPS;

    public static String url3 = Constants.API_URL_EYE;

    public static String FAVO =Constants.FAVS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

       String searchkey = getIntent().getStringExtra("keyword");
       System.out.println("huuuwwwwwwwwwwwwwwwww " + searchkey);

       if (searchkey.equals("fav")){
           Favorite = true;
          // Log.i("Faaaaaaaaaaaaaaaaaaa",Favorite);
       }
   else  if(searchkey.equals("facemakeup"))
        {
           url=  url1;
       }
       else if(searchkey.equals("lipsmakeup")) {
         url = url2;
     }
      else
          {
             url = url3;
         }

        Log.i("Video Activity","Checking");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
