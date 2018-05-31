package strawbericreations.com.makeupguide.userinterface;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.os.Bundle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.support.v7.app.AppCompatActivity;
import strawbericreations.com.makeupguide.R;
import strawbericreations.com.makeupguide.adapter.FavouriteAdapter;
import strawbericreations.com.makeupguide.adapter.VideoAdapter;
import strawbericreations.com.makeupguide.database.FavoritesContract;
import strawbericreations.com.makeupguide.model.Video;
import strawbericreations.com.makeupguide.utility.Constants;
import strawbericreations.com.makeupguide.widget.MakeupWidgetProvider;

import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class VideoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private static final int LOADER_ID = 1;

    private static final int LOADER_FAV = 2;


    private RecyclerView.LayoutManager mLayoutManager;

    public static String toPrint="";

    ArrayList<Video> dummy;

    public boolean check = false;

    public static VideoActivity mInstance;

    //   @BindView(R.id.recycler_video)
    RecyclerView recyclerView;

    public static String url;
    public  String fval;
    public static String arguments;

    //  public static String Favorite;
    public static boolean Favorite;

    public static String url1 = Constants.API_URL_FACE;

    public static String url2 = Constants.API_URL_LIPS;

    public static String url3 = Constants.API_URL_EYE;

    public String toSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
         String searchkey = getIntent().getStringExtra("keyword");
            System.out.println("huuuwwwwwwwwwwwwwwwww " + searchkey);

        dummy = new ArrayList<Video>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_video);
        mLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setHasFixedSize(true);
        ItemDecorator itemDecoration = new ItemDecorator(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);


            if (searchkey.equals("facemakeup")) {

                url = url1;
            } else if (searchkey.equals("lipsmakeup")) {

                url = url2;
            } else if (searchkey.equals("eyemakeup")) {

                url = url3;
            } else {
                fval = "fav";

                Log.i("favvvvvvv", fval);
            }
            System.out.println("aaaaaaaaaaaaa " + url);

        if(isNetworkAvailable(getApplicationContext())) {
            if (url != null && fval == null) {
                getSupportLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();

            } else {
                getSupportLoaderManager().initLoader(LOADER_FAV, null, this).forceLoad();

            }
            Log.i("Video Activity", "Checking");
        }
        else
            Toast.makeText(getApplicationContext(),"Please Connect to the INTERNET",Toast.LENGTH_LONG).show();
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

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        if (id == LOADER_ID) {
            Log.i("Proper Load", "coming or not");
            return new VideoListLoader(this);
        } else if (id == LOADER_FAV) {
           // return new FavoriteListLoader(getApplicationContext());
          String[] projection = new String[]{
                    FavoritesContract.FavoriteEntry.COLUMN_ID,
                    FavoritesContract.FavoriteEntry.COLUMN_IMAGE,
                    FavoritesContract.FavoriteEntry.COLUMN_TITLE};

            return new CursorLoader(this, FavoritesContract.FavoriteEntry.CONTENT_URI, projection, null, null, null);

        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        if (loader.getId() == LOADER_ID) {
           VideoAdapter myAdapter = new VideoAdapter(this, (ArrayList<Video>) data);
            recyclerView.setAdapter(myAdapter);
            Log.i("Adapter set", "OnLoadFinished");
            myAdapter.notifyDataSetChanged();
            updateWidget();
        }
        else if (loader.getId() == LOADER_FAV) {

         FavouriteAdapter fAdapter = new FavouriteAdapter(this);
            recyclerView.setAdapter(fAdapter);
            Log.i("Adapter set fav", "OnLoad");
            Log.i("Data coming", data.toString());
            fAdapter.swapCursor((Cursor) data);
            updateWidget();
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        recyclerView.setAdapter(null);
    }


    public static class VideoListLoader extends AsyncTaskLoader<ArrayList<Video>> {

        private static ArrayList<Video> videoArrayList;

        public VideoListLoader(Context context) {
            super(context);
        }

        @Override
        public ArrayList<Video> loadInBackground() {

            String result = "";
            URL urls;

            HttpURLConnection urlConnection = null;
            try {

                urls = new URL(url);

                urlConnection = (HttpURLConnection) urls.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("JSON dattttt", result);
            return parseJsonData(result);
        }

        public static ArrayList<Video> parseJsonData(String jsonstr) {

        ArrayList<String> widitem = new ArrayList<>();

            try {
                JSONObject object = new JSONObject(jsonstr);
                JSONArray items = object.getJSONArray("items");
                videoArrayList = new ArrayList<Video>();
                for (int i = 0; i < items.length(); i++) {
                    Video item = new Video();
                    JSONObject jObject = items.getJSONObject(i).getJSONObject("snippet");
                    Log.i("snippet", jObject.toString());
                    String title = jObject.getString("title");
                    Log.i("TITLE", title);
                    widitem.add(title);
                    item.setTitle(title);
                    JSONObject jObject1 = items.getJSONObject(i).getJSONObject("id");
                    String id = jObject1.getString("videoId");
                    Log.i("VideoID", id);
                    item.setId(id);
                    JSONObject jObject2 = items.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default");
                    String image = jObject2.getString("url");
                    Log.i("IMAGE", image);
                    item.setThumbnailURL(image);
                    videoArrayList.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i=0; i<widitem.size(); i++){
                toPrint +=  widitem.get(i) + "\n";
                Log.i("Widget data",toPrint);
            }
            return videoArrayList;
        }
    }

    private void updateWidget() {
        Intent i = new Intent(this, MakeupWidgetProvider.class);
          Log.i("updateWidget","Called from here");
          System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + toPrint);
          Log.i("String val", toPrint);
        Toast.makeText(getApplicationContext(), "from the activity",
                Toast.LENGTH_SHORT).show();
        i.putExtra("widget",toPrint);
        sendBroadcast(i);
    }

/*    public static class FavoriteListLoader extends AsyncTaskLoader<ArrayList<Video>> {


        private static ArrayList<Video> videos;

        public FavoriteListLoader(Context context) {
            super(context);
        }

        @Override
        public ArrayList<Video> loadInBackground() {

            Uri uri = FavoritesContract.FavoriteEntry.CONTENT_URI;
            ContentResolver resolver = mInstance.getApplicationContext().getContentResolver();
            Cursor cursor = null;

            try {
                videos = new ArrayList<Video>();
                cursor = resolver.query(uri, null, null, null, null);

                // clear movies
                videos.clear();

                if (cursor.moveToFirst()) {
                    do {
                        Video video = new Video();
                        //    movie.setReviews(cursor.getString(6));
                        //  movie.setTrailers(cursor.getString(7));
                        videos.add(video);
                    } while (cursor.moveToNext());
                }

            } finally {

                if (cursor != null)
                    cursor.close();
            }
            return videos;

        }
    }*/
}