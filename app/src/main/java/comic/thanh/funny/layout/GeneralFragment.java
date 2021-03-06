package comic.thanh.funny.layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import comic.thanh.funny.Common.ConvertDpToPx;
import comic.thanh.funny.Common.DatabaseEntertainment;
import comic.thanh.funny.GridSpacingItemDecoration;
import comic.thanh.funny.Model.Video;
import comic.thanh.funny.PlayVideoActivity;
import comic.thanh.funny.R;
import comic.thanh.funny.ViewHolder.VideoViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GeneralFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GeneralFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneralFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;

    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // constant video IS_GENERAL is true
    private static final boolean IS_GENERAL = true;

    // declare Recycler view
    RecyclerView recyclerView = null;

    // declare Firebase Database
    DatabaseReference videoList = null;

    FirebaseRecyclerAdapter<Video, VideoViewHolder> adapter = null;

    public GeneralFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GeneralFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GeneralFragment newInstance(String param1, String param2) {
        GeneralFragment fragment = new GeneralFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_general, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_general);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, ConvertDpToPx.dpToPx(getContext(), 2), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // init DatabaseEntertainment
        DatabaseEntertainment database = new DatabaseEntertainment(getContext());

        // load video general & suggest list
        if (database != null) {
            // get video database reference
            videoList = database.getVideo();
            // load video general
            loadVideoGeneral();
        }

        // after setting adapter, binding to recycler view
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }

        return rootView;
    }

    /**
     * Loading video is general
     */
    private void loadVideoGeneral() {
        adapter = new FirebaseRecyclerAdapter<Video, VideoViewHolder>(Video.class,
                R.layout.category_card,
                VideoViewHolder.class,
                videoList.orderByChild("isGeneral").equalTo(IS_GENERAL)) {
            @Override
            protected void populateViewHolder(VideoViewHolder viewHolder, Video model, int position) {
                // set video title
                viewHolder.txtTitle.setText(model.getTitle());
                // set image
                if (model.getImage().isEmpty()) {
                    viewHolder.imgVideo.setImageResource(R.drawable.ic_image_black_24dp);
                } else {
                    Picasso.with(getContext()).load(model.getImage())
                            .into(viewHolder.imgVideo);
                }
                // get current video
                final Video currentVideo = model;
                // set onClickListener imageView
                viewHolder.imgVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "" + currentVideo.getTitle(), Toast.LENGTH_SHORT).show();
                        sendingData(getContext(), currentVideo);
                    }
                });
                // set onClickListener
                viewHolder.btnView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "" + currentVideo.getTitle(), Toast.LENGTH_SHORT).show();
                        sendingData(getContext(), currentVideo);
                    }
                });
            }

            @Override
            public Video getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);
            }
        };
        recyclerView.setAdapter(adapter);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
