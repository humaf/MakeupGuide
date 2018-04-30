package strawbericreations.com.makeupguide.network;

import android.os.AsyncTask;
import android.util.Pair;
import java.util.List;
import strawbericreations.com.makeupguide.data.Video;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoListResponse;


/**
 * Created by redrose on 4/30/18.
 */

public class DownloadVideos
 extends AsyncTask<String, Void, Pair<String, List<Video>>>{

    private static final Long YOUTUBE_PLAYLIST_MAX_RESULTS = 10L;

    //see: https://developers.google.com/youtube/v3/docs/playlistItems/list
    private static final String YOUTUBE_PLAYLIST_PART = "snippet";
    private static final String YOUTUBE_PLAYLIST_FIELDS = "pageInfo,nextPageToken,items(id,snippet(resourceId/videoId))";
    //see: https://developers.google.com/youtube/v3/docs/videos/list
    private static final String YOUTUBE_VIDEOS_PART = "snippet,contentDetails,statistics"; // video resource properties that the response will include.
    private static final String YOUTUBE_VIDEOS_FIELDS = "items(id,snippet(title,description,thumbnails/high),contentDetails/duration,statistics)"; // selector specifying which fields to include in a partial response.

    private YouTube mYouTubeDataApi;


    @Override
    protected Pair<String, List<Video>> doInBackground(String... strings) {


        





        return null;
    }
}
