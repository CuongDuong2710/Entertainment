package topfunny.xuanthanh.video.layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import topfunny.xuanthanh.video.Common.Constants;
import topfunny.xuanthanh.video.Common.ConvertDpToPx;
import topfunny.xuanthanh.video.Common.DatabaseEntertainment;
import topfunny.xuanthanh.video.GridSpacingItemDecoration;
import topfunny.xuanthanh.video.Model.Video;
import topfunny.xuanthanh.video.R;
import topfunny.xuanthanh.video.ViewHolder.VideoViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartoonsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartoonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartoonsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // declare Recycler view
    RecyclerView recyclerView = null;

    // declare Firebase Database
    DatabaseReference videoList = null;

    FirebaseRecyclerAdapter<Video, VideoViewHolder> adapter = null;

    public CartoonsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartoonsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CartoonsFragment newInstance(String param1, String param2) {
        CartoonsFragment fragment = new CartoonsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_music_for_kids, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_music_for_kids);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, ConvertDpToPx.dpToPx(getContext(), 2), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // init DatabaseEntertainment
        DatabaseEntertainment database = new DatabaseEntertainment(getContext());

        // set adapter & load suggest list
        if (database != null) {
            videoList = database.getVideo();
            adapter = database.loadVideo(getContext(), Constants.CARTOONS);
        }

        // after setting adapter, binding to recycler view
        if (adapter != null) {
            recyclerView.setAdapter(adapter);
        }

        return rootView;
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