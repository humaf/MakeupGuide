package strawbericreations.com.makeupguide.userinterface;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import strawbericreations.com.makeupguide.R;
import strawbericreations.com.makeupguide.Utility.Constants;
import strawbericreations.com.makeupguide.adapter.VideoAdapter;
import strawbericreations.com.makeupguide.data.Video;

public class VideoFragment extends Fragment {
    @BindView(R.id.recycler_video)
    RecyclerView recyclerView;

    public VideoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
     //   String strtext = getArguments().getString("searchkey");
        // Inflate the layout for this fragment

        //     if (!isNetworkAvailable(this) == false) {

        DownloadVideos task = new DownloadVideos();
        task.execute("https://www.googleapis.com/youtube/v3/search?part=snippet&q=eyemakeup&type=video&key=AIzaSyBwuVK3GeGODr9F_XtEoq6MieFfCsLEblE");
   /*   }
        else
            networkerror.show();
*/
        return rootView;
    }

    public class DownloadVideos extends AsyncTask<String, Void, ArrayList<Video>> {

        private RecyclerView mrecyclerView;

        ArrayList<Video> VideoList ;

        @Override
        protected ArrayList<Video> doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

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
            try {

                JSONArray results = new JSONArray(result);
                VideoList = new ArrayList<Video>();
                for (int i = 0; i < results.length(); i++) {
                    JSONObject res = results.optJSONObject(i);
                    Video item = new Video();
                    String title = (res.optString("title"));
                    item.setTitle(title);
                    String id = (res.optString("id"));
                    item.setId(id);
                    Log.i("title", title);
                    VideoList.add(item);
                    Log.i("video", VideoList.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return VideoList;

        }


        @Override
        protected void onPostExecute(ArrayList<Video> result) {
          VideoAdapter  mVideoAdapter = new VideoAdapter(getContext(), result);
            mrecyclerView.setAdapter(mVideoAdapter);
            Log.i("checking data", result.toString());
        }
    }

}



