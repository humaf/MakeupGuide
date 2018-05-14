package strawbericreations.com.makeupguide.userinterface;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import strawbericreations.com.makeupguide.R;
import strawbericreations.com.makeupguide.adapter.VideoAdapter;
import strawbericreations.com.makeupguide.model.Video;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
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

public class VideoFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Video>> {

    private static final int LOADER_ID = 1;

    private Context mContext;

    private VideoAdapter myAdapter;

    ArrayList<Video> dummy;

    private static String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=eyemakeup&type=video&maxResults=20&key=AIzaSyBwuVK3GeGODr9F_XtEoq6MieFfCsLEblE";

    //   @BindView(R.id.recycler_video)
    RecyclerView recyclerView;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.i("Coming till here", "frag");
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        dummy = new ArrayList<Video>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_video);

      //  myAdapter = new VideoAdapter(getActivity(),dummy);
       // recyclerView.setAdapter(myAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager); // set LayoutManager to RecyclerView
        recyclerView.setHasFixedSize(true);
        Log.i("start", "from here");
        getLoaderManager().initLoader(LOADER_ID, null, this).forceLoad();
        return rootView;
    }

    @Override
    public Loader<ArrayList<Video>> onCreateLoader(int id, Bundle args) {
        return new VideoListLoader(getActivity());
    }


    @Override
    public void onLoadFinished(Loader<ArrayList<Video>> loader, ArrayList<Video> data) {
        myAdapter=new VideoAdapter(getActivity(),data);
        recyclerView.setAdapter(myAdapter);
     ///   myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Video>> loader) {

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
            Log.i("Video Array",videoArrayList.toString());
            return videoArrayList;
        }
    }


}

