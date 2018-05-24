package strawbericreations.com.makeupguide.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import strawbericreations.com.makeupguide.R;
import strawbericreations.com.makeupguide.database.FavoritesContract;
import strawbericreations.com.makeupguide.model.Video;
import strawbericreations.com.makeupguide.userinterface.DetailActivity;

/**
 * Created by redrose on 5/24/18.
 */

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    private static final String EXTRA_VIDEO = "video";
    private Context fContext;
    private Cursor cursor;


    public FavouriteAdapter(Context context) {

        this.fContext = context;
    }

    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FavouriteAdapter.ViewHolder holder, int position) {

        if (cursor == null || cursor.getCount() == 0) {
            return;
        }
        cursor.moveToPosition(position);

        String title = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoriteEntry.COLUMN_TITLE));

        holder.titleView.setText(title);

        String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoriteEntry.COLUMN_IMAGE));

        Picasso.with(holder.imageView.getContext()).load(imageUrl).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        if (cursor != null) {
            notifyDataSetChanged();
        }
    }
    public  class ViewHolder extends RecyclerView.ViewHolder {//implements View.OnClickListener {
        @BindView(R.id.text_view)
        TextView titleView;

        @BindView(R.id.image_item)
        ImageView imageView;

        //  private OnItemClickListener onItemClickListener;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent in = new Intent(fContext, DetailActivity.class);
                    Video video = getCurrentVideo(getAdapterPosition());
                    in.putExtra(EXTRA_VIDEO,video);
                    fContext.startActivity(in);

                }
            });
            //  titleView.setOnClickListener(this);
        }

        private Video getCurrentVideo(int adapterPosition) {

            Video video = new Video();
            String videoId = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoriteEntry.COLUMN_ID));
            video.setId(videoId);
            return video;
        }

    }








    /*    public void setOnItemClickListener(OnItemClickListener iListener){
            this.onItemClickListener = iListener;
        }
        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(view,getAdapterPosition());
        }*/
}




