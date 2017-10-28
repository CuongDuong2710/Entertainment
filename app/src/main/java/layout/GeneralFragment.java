package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import organization.tho.entertaiment.Common.ConvertDpToPx;
import organization.tho.entertaiment.Common.DatabaseEntertainment;
import organization.tho.entertaiment.GridSpacingItemDecoration;
import organization.tho.entertaiment.Model.Video;
import organization.tho.entertaiment.PlayVideoActivity;
import organization.tho.entertaiment.R;
import organization.tho.entertaiment.ViewHolder.VideoViewHolder;

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
    private static final String IS_GENERAL = "true";

    // declare Recycler view
    RecyclerView recyclerView = null;

    // declare Firebase Database
    DatabaseReference videoList = null;

    FirebaseRecyclerAdapter<Video, VideoViewHolder> adapter = null;

    // declare material search bar
    MaterialSearchBar materialSearchBar;

    // search functionality
    FirebaseRecyclerAdapter<Video, VideoViewHolder> searchAdapter = null;
    List<String> suggestList = new ArrayList<>();

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
        materialSearchBar = rootView.findViewById(R.id.search_bar_general);

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
            // load suggest list
            loadSuggestList();
        }

        // after setting adapter, binding to recycler view
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }

        // set suggest list to search bar
        materialSearchBar.setHint("Enter your video");
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);

        // add text change listener
        addTextChangeListener();

        // set search on listener
        onSearchActionListener();

        return rootView;
    }

    /**
     * Loading suggest list
     */
    private void loadSuggestList() {
        if (videoList != null) {
            videoList.orderByChild("isGeneral").equalTo(IS_GENERAL)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // get videoList item
                            for(DataSnapshot itemSpapshot : dataSnapshot.getChildren()) {
                                Video video = itemSpapshot.getValue(Video.class);
                                suggestList.add(video.getTitle());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
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
                Picasso.with(getContext()).load(model.getImage())
                        .into(viewHolder.imgVideo);
                // get current video
                final Video currentVideo = model;
                // set onClickListener
                viewHolder.imgVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "" + currentVideo.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        // sending data
                        sendingData(getContext(), currentVideo);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
    }

    /**
     * set text change listener for Search bar
     */
    private void addTextChangeListener() {
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // When user type their text, we will change suggest list
                List<String> suggest = new ArrayList<>();
                for (String search : suggestList) {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase())) {
                        suggest.add(search);
                    }
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * set on search action listener
     */
    private void onSearchActionListener() {
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                // When Search bar is close
                // Restore original adapter
                if (!enabled) {
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                // When Search finished
                // Show result of search adapter
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });
    }

    /**
     * Searching videoList by text
     * @param text
     */
    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Video, VideoViewHolder>(
                Video.class,
                R.layout.category_card,
                VideoViewHolder.class,
                videoList.orderByChild("title").equalTo(text.toString())) { // Compare video title
            @Override
            protected void populateViewHolder(VideoViewHolder viewHolder, final Video model, int position) {
                // set title video
                viewHolder.txtTitle.setText(model.getTitle());
                // set video image
                Picasso.with(getContext()).load(model.getImage())
                        .into(viewHolder.imgVideo);
                viewHolder.imgVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "" + model.getTitle(), Toast.LENGTH_SHORT).show();
                        sendingData(getContext(), model);
                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);
    }

    /**
     * Sending data to PlayVideo activity
     */
    private void sendingData(Context context, Video video) {
        if (context != null) {
            Intent playVideo = new Intent(context, PlayVideoActivity.class);
            playVideo.putExtra("videoId", video.getVideoId());
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
