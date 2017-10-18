package organization.tho.entertaiment.Common;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import organization.tho.entertaiment.Model.Video;
import organization.tho.entertaiment.R;
import organization.tho.entertaiment.ViewHolder.VideoViewHolder;

/**
 * Created by QUOC CUONG on 17/10/2017.
 * Loading video by category
 */

public class DatabaseEntertainment {
    private FirebaseDatabase database;
    private com.google.firebase.database.DatabaseReference video;
    private FirebaseRecyclerAdapter<Video, VideoViewHolder> adapter;

    public DatabaseEntertainment() {
        database = FirebaseDatabase.getInstance();
        video = database.getReference("Video");
    }

    /**
     * Loading video by category
     * @param context
     * @param layoutId
     * @param category
     */
    public FirebaseRecyclerAdapter loadVideo(final Context context, String category) {
        adapter = new FirebaseRecyclerAdapter<Video, VideoViewHolder>(Video.class,
                R.layout.category_card,
                VideoViewHolder.class,
                video.orderByChild("CategoryId").equalTo(category)) {
            @Override
            protected void populateViewHolder(VideoViewHolder viewHolder, Video model, int position) {
                // set video title
                viewHolder.txtTitle.setText(model.getTitle());
                // set image
                Picasso.with(context).load(model.getImage())
                        .into(viewHolder.imgVideo);
                // get current video
                final Video currentVideo = model;
                // set onClickListener
                viewHolder.imgVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "" + currentVideo.getTitle(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        return adapter;
    }
}
