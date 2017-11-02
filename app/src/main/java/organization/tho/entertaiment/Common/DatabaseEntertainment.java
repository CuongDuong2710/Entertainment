package organization.tho.entertaiment.Common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import organization.tho.entertaiment.Model.Video;
import organization.tho.entertaiment.PlayVideoActivity;
import organization.tho.entertaiment.R;
import organization.tho.entertaiment.ViewHolder.VideoViewHolder;

/**
 * Created by QUOC CUONG on 17/10/2017.
 * Loading video by category
 */

public class DatabaseEntertainment {
    private FirebaseDatabase database = null;
    private com.google.firebase.database.DatabaseReference video = null, connectedRef = null;
    private FirebaseRecyclerAdapter<Video, VideoViewHolder> adapter = null;

    // suggest list when searching
    private List<String> suggestList = new ArrayList();

    public DatabaseEntertainment(final Context context) {
        database = FirebaseDatabase.getInstance();
        video = database.getReference("video");
        video.keepSynced(true);

        connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = dataSnapshot.getValue(Boolean.class);
                if (connected) {

                } else {
                    if (context != null) {
                        Toast.makeText(context, "Connect Wifi to watch videos!", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Loading video by category
     * @param context
     * @param category
     */
    public FirebaseRecyclerAdapter loadVideo(final Context context, String category) {
        adapter = new FirebaseRecyclerAdapter<Video, VideoViewHolder>(Video.class,
                R.layout.category_card,
                VideoViewHolder.class,
                video.orderByChild("categoryId").equalTo(category)) {
            @Override
            protected void populateViewHolder(VideoViewHolder viewHolder, Video model, int position) {
                // set video title
                viewHolder.txtTitle.setText(model.getTitle());
                // set image
                Picasso.with(context).load(model.getImage())
                        .into(viewHolder.imgVideo);
                // get current video
                final Video currentVideo = model;
                // set onClickListener imageView
                viewHolder.imgVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "" + currentVideo.getTitle(), Toast.LENGTH_SHORT).show();
                        sendingData(context, currentVideo);
                    }
                });

                // set onClickListener btnView
                viewHolder.btnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "" + currentVideo.getTitle(), Toast.LENGTH_SHORT).show();
                        sendingData(context, currentVideo);
                    }
                });
            }

            @Override
            public Video getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);
            }
        };
        return adapter;
    }

    /**
     * Sending data to PlayVideo activity
     */
    private void sendingData(Context context, Video video) {
        if (context != null) {
            Intent playVideo = new Intent(context, PlayVideoActivity.class);
            playVideo.putExtra("videoId", video.getVideoId());
            playVideo.putExtra("videoTitle", video.getTitle());
            context.startActivity(playVideo);
        }
    }

    public DatabaseReference getVideo() {
        return video;
    }
}
