package strawbericreations.com.makeupguide.adapter;

/**
 * Created by redrose on 5/7/18.
 */

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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import strawbericreations.com.makeupguide.R;
import strawbericreations.com.makeupguide.model.Video;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private ArrayList<Video> mvideoItemList = new ArrayList<Video>() ;
    private Context mContext;
    private int layoutResourceId;
    private LayoutInflater inflater;
    private RecyclerView video_recyler;


    public void addAll(List<Video> videoData) {
        mvideoItemList= (ArrayList<Video>) videoData;
    }

   public VideoAdapter(Context context, ArrayList<Video> videoItemList) {
        this.mvideoItemList = videoItemList;
        this.mContext = context;
    }

    public int getItemCount() {
       Log.i("size null or not", String.valueOf(mvideoItemList.size()));
        return mvideoItemList.size();
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new VideoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        final Video video = mvideoItemList.get(position);
        Log.i("possssssssssssss", String.valueOf(position));
        //     final Steps step = stepItemList.get(position);
        System.out.println("----p" + position);
        String imageUrl = video.getThumbnailURL();
        Log.i("Adapter image",imageUrl);

        if (imageUrl != null) {
        //    Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(mContext).load(imageUrl).into(holder.imageView);
        }

        holder.titleView.setText(video.getTitle());
    }



    public static class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.text_view)
        TextView titleView;

        @BindView(R.id.image_item)
        ImageView imageView;


        public VideoHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
//            titleView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}