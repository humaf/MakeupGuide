<<<<<<< HEAD
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

    private ArrayList<Video> mvideoItemList  ;
    private Context mContext;
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

 /*   @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;
        Log.i("getview", "getview");
        if (row == null) {
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleView = (TextView) row.findViewById(R.id.text_view);
            holder.imageView = (ImageView) row.findViewById(R.id.image_item);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Movie item = movieItemList.get(position);
        String iurl = item.getImage();
        Log.i("Imageis", iurl);
        Log.i("Movie in Adapter", item.toString());

        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w92/" + movieItemList.get(position)
                .getImage()).resize(320, 500).placeholder(R.drawable.placeholder).
                into(holder.imageView);
        return row;
    }
*/
    @Override
    public void onBindViewHolder(VideoHolder holder, int position) {
        for(int i=0;i<mvideoItemList.size();i++){
            Log.i("values",mvideoItemList.get(i).getTitle());
        }
        Log.i("Bindr position",String.valueOf(position));
       Video video = mvideoItemList.get(position);
       String title = video.getTitle();
       Log.i("ttttttttttttttt",title);
       Log.i("holder pos" ,String.valueOf(holder.getAdapterPosition()));
         holder.titleView.setText(video.getTitle());
      //  holder.titleView.setText(mvideoItemList.get(position).getTitle());
        Log.i("Adapter title",mvideoItemList.get(position).getTitle().toString());
        Log.i("possssssssssssss", String.valueOf(position));
        String imageUrl = video.getThumbnailURL();
        Log.i("Adapter image",imageUrl);

        if (imageUrl != null) {
            //    Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            //  Picasso.with(mContext).load(imageUrl).into(holder.imageView);
            Picasso.with(holder.imageView.getContext()).load(imageUrl).into(holder.imageView);
        }

    }
    public int getItemCount() {
        Log.i("size null or not", String.valueOf(mvideoItemList.size()));
        return mvideoItemList.size();
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
||||||| merged common ancestors
=======
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
>>>>>>> c501a30b4325b578475ffab73abe70e3b7811a45
