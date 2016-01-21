package com.rdcc.wepay.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.Parse.CloudHandler;
import com.rdcc.wepay.Cloud.User;
import com.rdcc.wepay.R;

public class GroupViewAddFundsFragment extends Fragment{
    Data data;
    User user;
    FragmentCommunicator comm;
    CloudHandler cloud;
    Button pay;
    EditText addAmount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_addfunds, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (FragmentCommunicator) getActivity();
        data = comm.retrieveData();
        user = comm.retrieveUser();
        cloud = new CloudHandler();
        //create our view
        final ImageView icon = (ImageView) getActivity().findViewById(R.id.groupIcon);
        icon.setImageResource(data.getDrawable());
        final TextView groupname = (TextView) getActivity().findViewById(R.id.groupName);
        groupname.setText(data.getName());
        final TextView funds = (TextView) getActivity().findViewById(R.id.groupFunds);
        funds.setText("Funds: $" + String.format("%.0f", data.getGroupFunds()));
        final TextView memNum = (TextView) getActivity().findViewById(R.id.numberofMembers);
        memNum.setText("Number of Members: " + cloud.getNumberOfMembersInGroup(data.getGroupID()));
        final TextView groupdesc = (TextView) getActivity().findViewById(R.id.groupDescription);
        groupdesc.setText(data.getDescription());

        addAmount = (EditText) getActivity().findViewById(R.id.addAmount);
        pay = (Button) getActivity().findViewById(R.id.addPay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!addAmount.getText().toString().equals("")){
                    data.setGroupFunds(data.getGroupFunds() + Float.parseFloat(addAmount.getText().toString()));
                    data.setPayHistory(data.getPayHistory() +
                            "\nUser: " + user.getName() + " has made a payment of: $" + addAmount.getText().toString() + "\n------\n");
                    user.setFunds(user.getFunds() - Float.parseFloat(addAmount.getText().toString()));

                    cloud.updateFundsviaUserID(user);
                    cloud.updateFundsviaGroupID(data);

                    comm.setData(data);
                    comm.setUser(user);

                    Toast.makeText(getActivity(), "Thank you for your Payment", Toast.LENGTH_LONG).show();
                    addAmount.setText("");
                    comm.response(0,2);
                }
            }
        });

    }
}

