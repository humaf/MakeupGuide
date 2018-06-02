package strawbericreations.com.makeupguide.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    private Context fContext;
    private Cursor cursor;
    String title;
    String imageUrl;
    String id;

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

        id = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoriteEntry.COLUMN_ID));

         title = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoriteEntry.COLUMN_TITLE));

        holder.titleView.setText(title);

        imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoriteEntry.COLUMN_IMAGE));

        Picasso.with(holder.imageView.getContext()).load(imageUrl).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        Log.i("Cursor size",String.valueOf(cursor.getCount()));
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

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent in = new Intent(fContext, DetailActivity.class);
                    Video video = getCurrentVideo(getAdapterPosition());
                  String vid = video.getId();
                  String tit = video.getTitle();
                  String img = video.getThumbnailURL() ;
                    in.putExtra("id", video.getId());
                    in.putExtra("VideoId",vid);
                   in.putExtra("Title",tit);
                   in.putExtra("Image",img);
                    fContext.startActivity(in);
                }
            });

        }

        private Video getCurrentVideo(int adapterPosition) {
            cursor.moveToPosition(adapterPosition);
            Video video = new Video();
            String videoId = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoriteEntry.COLUMN_ID));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoriteEntry.COLUMN_IMAGE));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(FavoritesContract.FavoriteEntry.COLUMN_TITLE));

            video.setId(videoId);
            video.setThumbnailURL(image);
            video.setTitle(title);
            return video;
        }
    }
}




