package strawbericreations.com.makeupguide.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import strawbericreations.com.makeupguide.R;
import strawbericreations.com.makeupguide.data.Video;

/**
 * Created by redrose on 4/30/18.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private ArrayList<Video> videoItemList = new ArrayList<Video>();
    private Context mContext;
    private int layoutResourceId;
    private LayoutInflater inflater;

    public VideoAdapter(Context context, ArrayList<Video> videoItemList) {
        this.videoItemList = videoItemList;
        this.mContext = context;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new VideoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        final  Video video = videoItemList.get(position);
        //     final Steps step = stepItemList.get(position);
        System.out.println("------------------------" + position);
        String imageUrl = video.getThumbnailURL();

   /*     if (imageUrl != null) {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(getContext()).load(builtUri).into(holder.imageView);
        }
*/
        holder.titleView.setText(video.getTitle());
    }

   @Override
    public int getItemCount() {

        return 1;
     //   return videoItemList.size();
    }

    public static class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titleView;
        ImageView imageView;

        public VideoHolder(View view){
            super(view);
            ButterKnife.bind(this,view);
            titleView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

        }
    }
}
