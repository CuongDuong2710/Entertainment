package organization.tho.entertaiment.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import organization.tho.entertaiment.R;

/**
 * Created by QUOC CUONG on 17/10/2017.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {
    public TextView txtTitle;
    public ImageView imgVideo;

    public VideoViewHolder(View itemView) {
        super(itemView);

        txtTitle = itemView.findViewById(R.id.title);
        imgVideo = itemView.findViewById(R.id.thumbnail);
    }
}
