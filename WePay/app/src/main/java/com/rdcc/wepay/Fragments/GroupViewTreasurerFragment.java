package com.rdcc.wepay.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.Parse.CloudHandler;
import com.rdcc.wepay.Cloud.User;
import com.rdcc.wepay.R;

public class GroupViewTreasurerFragment extends Fragment implements View.OnClickListener {
    Data data;
    User user;
    FragmentCommunicator comm;
    CloudHandler cloud;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_treasurer, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (FragmentCommunicator) getActivity();
        data = comm.retrieveData();
        user = comm.retrieveUser();
        cloud = new CloudHandler();

        final TextView name = (TextView) getActivity().findViewById(R.id.groupName);
        name.setText(data.getName());

        final TextView history = (TextView) getActivity().findViewById(R.id.history);
        history.setText("Payment History\n~~~~~~~~~~~~~~~\n" + data.getPayHistory());

        final Button cashOut = (Button) getActivity().findViewById(R.id.button2);
        cashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.setPayHistory(data.getPayHistory() +
                        "\nTreasurer: " + user.getName() + " has taken funds worth: $" + (int) data.getGroupFunds() + "\n------\n");
                user.setFunds(user.getFunds() + data.getGroupFunds());
                data.setGroupFunds(0);

                //update funds for both user and group
                cloud.updateFundsviaGroupID(data);
                cloud.updateFundsviaUserID(user);

                comm.setData(data);
                comm.setUser(user);
                Toast.makeText(getActivity(), "Funds has been transferred to your account", Toast.LENGTH_LONG).show();
                comm.response(0,2);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
