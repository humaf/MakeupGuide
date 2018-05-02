/*package strawbericreations.com.makeupguide.network;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import strawbericreations.com.makeupguide.R;
import strawbericreations.com.makeupguide.adapter.VideoAdapter;
import strawbericreations.com.makeupguide.data.Video;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by redrose on 4/30/18.
 */
/*
public class DownloadVideos extends AsyncTask<String, Void, ArrayList<Video>>{

    private RecyclerView mrecyclerView;
    VideoAdapter mVideoAdapter;
 @Override
    protected ArrayList<Video> doInBackground(String... urls) {
        ArrayList<Video> VideoList = null;

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
            JSONObject response = new JSONObject(result);
            JSONArray results = response.optJSONArray("results");
            VideoList = new ArrayList<Video>();
            for (int i = 0; i < results.length(); i++) {
                JSONObject res = results.optJSONObject(i);
                Video item = new Video();
                String title = (res.optString("title"));
                item.setTitle(title);
                String id = (res.optString("id"));
                item.setId(id);
                Log.i("Title", title);

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
         mVideoAdapter = new VideoAdapter(getContext(),result);
        mrecyclerView.setAdapter(mVideoAdapter);
        Log.i("checking data", result.toString());
    }
}
*/