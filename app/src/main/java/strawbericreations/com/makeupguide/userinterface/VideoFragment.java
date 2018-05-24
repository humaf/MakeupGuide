package strawbericreations.com.makeupguide.userinterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import strawbericreations.com.makeupguide.R;
import strawbericreations.com.makeupguide.adapter.FavouriteAdapter;
import strawbericreations.com.makeupguide.adapter.VideoAdapter;
import strawbericreations.com.makeupguide.database.FavoritesContract;
import strawbericreations.com.makeupguide.model.Video;

import android.content.ContentResolver;
//import android.content.CursorLoader;
import android.support.v4.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static strawbericreations.com.makeupguide.userinterface.VideoActivity.Favorite;
import static strawbericreations.com.makeupguide.userinterface.VideoActivity.url;

public class VideoFragment extends Fragment implements LoaderManager.LoaderCallbacks {

    private static final int LOADER_ID = 1;

    private static final int LOADER_FAV = 2;

    private VideoAdapter myAdapter;

    private FavouriteAdapter fAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Video> dummy;

    public static VideoActivity mInstance;

    public String fav ="yes";


    //   @BindView(R.id.recycler_video)
    RecyclerView recyclerView;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mInstance = (VideoActivity) getActivity();
        Log.i("Coming till here", "frag");
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        dummy = new ArrayList<Video>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_video);
        mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setHasFixedSize(true);
        Log.i("start", "from here");

       if(Favorite) {
            getLoaderManager().initLoader(LOADER_FAV, null, this).forceLoad();
       }
        else
       {
            getLoaderManager().initLoader(LOADER_ID,null,this).forceLoad();
        }

        return rootView;
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        if (id == LOADER_ID) {
            Log.i("Proper Load", "coming or not");
            return new VideoListLoader(getActivity());
        } else if (id == LOADER_FAV) {
            // return new FavoriteListLoader(getActivity());
            String[] projection = new String[]{
                    FavoritesContract.FavoriteEntry.COLUMN_ID,
                    FavoritesContract.FavoriteEntry.COLUMN_IMAGE,
                    FavoritesContract.FavoriteEntry.COLUMN_TITLE};

            return new CursorLoader(getActivity(), FavoritesContract.FavoriteEntry.CONTENT_URI, projection, null, null, null);
        }

        return null;
    }

    public void onLoadFinished(Loader loader,Object data) {
        if(loader.getId()==LOADER_ID){
            myAdapter = new VideoAdapter(getActivity(), (ArrayList<Video>) data);
            recyclerView.setAdapter(myAdapter);
            Log.i("Adapter set", "OnLoadFinished");
            myAdapter.notifyDataSetChanged();

        }
        else if(loader.getId()==LOADER_FAV){
            fAdapter =  new FavouriteAdapter(getContext());
            recyclerView.setAdapter(fAdapter);
            Log.i("Adapter set fav","OnLoad");
            Log.i("Data coming",data.toString());
            fAdapter.swapCursor((Cursor)data);
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

         //   final ArrayList<Video> videoArrayList = new ArrayList<Video>();
            try {
                JSONObject object = new JSONObject(jsonstr);
                JSONArray items = object.getJSONArray("items");
                videoArrayList = new ArrayList<Video>();
                for (int i = 0; i < items.length(); i++) {
                    Video item = new Video();
                    JSONObject jObject = items.getJSONObject(i).getJSONObject("snippet");
                    Log.i("snippet",jObject.toString());
                    String title = jObject.getString("title");
                    Log.i("TITLE",title);
                    item.setTitle(title);
                    JSONObject jObject1 = items.getJSONObject(i).getJSONObject("id");
                    String id = jObject1.getString("videoId");
                    Log.i("VideoID",id);
                    item.setId(id);
                    JSONObject jObject2 = items.getJSONObject(i).getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("default");
                    String image = jObject2.getString("url");
                    Log.i("IMAGE",image);
                    item.setThumbnailURL(image);
                    videoArrayList.add(item);

                }
                } catch(JSONException e){
                    e.printStackTrace();
                }
//            Log.i("vvvvvvvvvvvv",videoArrayList.toString());
            return videoArrayList;
        }
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
    }
*/
}

