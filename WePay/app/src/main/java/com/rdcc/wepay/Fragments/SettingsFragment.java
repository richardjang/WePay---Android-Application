package com.rdcc.wepay.Fragments;

//User Profile::
//Name and Icon
//User ID
//Payment Methods
//Set PIN
//Sound
//Log out

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rdcc.wepay.Cloud.User;
import com.rdcc.wepay.LoginActivity;
import com.rdcc.wepay.R;

public class SettingsFragment extends Fragment{
    FragmentCommunicator comm;
    User userData;
    TextView username, userID;
    ImageView profpic;
    Button logout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
//        comm = (FragmentCommunicator) getActivity();
//        userData = comm.retrieveUser();

//        username = (TextView) getActivity().findViewById(R.id.profileName);
//        userID = (TextView) getActivity().findViewById(R.id.profileID);
//        profpic = (ImageView) getActivity().findViewById(R.id.profilePic);

//        username.setText(userData.getName());
//        userID.setText("ID: " + userData.getID());
//        profpic.setImageResource(userData.getBitmap());

        logout = (Button) getActivity().findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: LOGOUT
                Toast.makeText(getActivity(), "Logged Out", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.layout_settings, container, false);
    }
}
