
package strawbericreations.com.makeupguide.adapter;

/**
 * Created by redrose on 5/7/18.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import strawbericreations.com.makeupguide.R;
import strawbericreations.com.makeupguide.model.Video;
import strawbericreations.com.makeupguide.userinterface.DetailActivity;
import strawbericreations.com.makeupguide.userinterface.OnItemClickListener;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder> {

    private ArrayList<Video> mvideoItemList  ;
    private static Context mContext;
    private OnItemClickListener onItemClickListener;
    private int layoutResourceId;
    private LayoutInflater inflater;
    private RecyclerView video_recyler;


   public VideoAdapter(Context context, ArrayList<Video> videoItemList) {
        this.mvideoItemList = videoItemList;
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

        Log.i("Bindr position",String.valueOf(position));
       Video video = mvideoItemList.get(position);
     final  String title = video.getTitle();

       Log.i("holder pos" ,String.valueOf(holder.getAdapterPosition()));
         holder.titleView.setText(video.getTitle());
      final  String imageUrl = video.getThumbnailURL();
        Log.i("Adapter image",imageUrl);

      final  String videoId = video.getId();


        if (imageUrl != null) {
            Picasso.with(holder.imageView.getContext()).load(imageUrl).into(holder.imageView);
        }
        holder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("gottcha!!");
                final  Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("VideoId",videoId);
                intent.putExtra("Image",imageUrl);
                intent.putExtra("Title",title);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
//        Log.i("size in Adapter", String.valueOf(mvideoItemList.size()));
        return mvideoItemList.size();
    }


    public static class VideoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.text_view)
        TextView titleView;

        @BindView(R.id.image_item)
        ImageView imageView;

        private OnItemClickListener onItemClickListener;


        public VideoHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
           titleView.setOnClickListener(this);
        }

        public void setOnItemClickListener(OnItemClickListener iListener){
            this.onItemClickListener = iListener;
        }
        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view,getAdapterPosition());
        }
    }
}
