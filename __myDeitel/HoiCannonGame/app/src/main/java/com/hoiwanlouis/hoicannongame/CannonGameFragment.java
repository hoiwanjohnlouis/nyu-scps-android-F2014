package com.hoiwanlouis.hoicannongame;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class CannonGameFragment extends Fragment
{

    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // custom view to display the game
    private CannonView cannonView;

    //
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.i(DEBUG_TAG, "in onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        // get the CannonView
        cannonView = (CannonView) view.findViewById(R.id.cannonView);
        return view;
    } // onCreateView()

    // set up volume control once Activity is created
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        Log.i(DEBUG_TAG, "in onActivityCreated()");
        super.onActivityCreated(savedInstanceState);

        // allow volume keys to set game volume
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
    } // end method onActivityCreated()

    // when MainActivity is paused, CannonGameFragment terminates the game
    @Override
    public void onPause()
    {
        Log.i(DEBUG_TAG, "in onPause()");
        super.onPause();
        // terminates the game
        cannonView.stopGame();
    } // end method onPause()

    // when MainActivity is paused, CannonGameFragment releases resources
    @Override
    public void onDestroy()
    {
        Log.i(DEBUG_TAG, "in onDestroy()");
        super.onDestroy();
        cannonView.releaseResources();
    } // end method onDestroy()
} // end class CannonGameFragment
