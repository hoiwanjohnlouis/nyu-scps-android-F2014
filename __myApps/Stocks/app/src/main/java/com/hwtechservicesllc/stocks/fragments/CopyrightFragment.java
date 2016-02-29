package com.hwtechservicesllc.stocks.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hwtechservicesllc.stocks.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CopyrightFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CopyrightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CopyrightFragment extends Fragment {

    // default fragment bundle key 1: ARG_ITEM_STRING, aka ARG_PARAM1
    private static final String ARG_ROW_ID = FragmentConstants.ROW_ID;

    // the fragment bundle data 1: VALUE DATA, aka mParam1
    private long mRowId;

    //
    // callback interface/method
    //
    private OnFragmentInteractionListener mListener;

    /****************************************************************
     *
     * Start of non-template variables
     *
     ****************************************************************/

    //
    // logging purposes
    //
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    public static CopyrightFragment newInstance() {
        Log.i("CopyrightFragment", "in newInstance()");
        CopyrightFragment fragment = new CopyrightFragment();
        return fragment;
    }

    public CopyrightFragment() {
        Log.i(DEBUG_TAG, "in Constructor");
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle copyrightFragmentInterfaceBundle) {
        Log.i(DEBUG_TAG, "in onButtonPressed()");
        if (mListener != null) {
            mListener.onCopyrightFragmentSymbolCompleted(copyrightFragmentInterfaceBundle);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRowId = getArguments().getLong(ARG_ROW_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_copyright, container, false);
    }

    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // really a no-op callback since this is a display only fragment
        public void onCopyrightFragmentSymbolCompleted(Bundle copyrightFragmentInterfaceBundle);
    }

}
