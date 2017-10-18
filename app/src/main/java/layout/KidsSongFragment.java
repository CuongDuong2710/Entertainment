package layout;

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
import com.google.firebase.database.FirebaseDatabase;

import organization.tho.entertaiment.Common.Constants;
import organization.tho.entertaiment.Common.ConvertDpToPx;
import organization.tho.entertaiment.Common.DatabaseEntertainment;
import organization.tho.entertaiment.GridSpacingItemDecoration;
import organization.tho.entertaiment.Model.Video;
import organization.tho.entertaiment.R;
import organization.tho.entertaiment.ViewHolder.VideoViewHolder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link KidsSongFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link KidsSongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KidsSongFragment extends Fragment {
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
    FirebaseDatabase database = null;
    DatabaseReference video = null;

    FirebaseRecyclerAdapter<Video, VideoViewHolder> adapter = null;

    public KidsSongFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KidsSongFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KidsSongFragment newInstance(String param1, String param2) {
        KidsSongFragment fragment = new KidsSongFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_kids_song, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_kids_song);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, ConvertDpToPx.dpToPx(getContext(), 2), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // init DatabaseEntertainment
        DatabaseEntertainment database = new DatabaseEntertainment(getContext());

        // set adapter
        adapter = database.loadVideo(getContext(), Constants.KID_SONGS);

        // after setting adapter, binding to recycler view
        recyclerView.setAdapter(adapter);

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
