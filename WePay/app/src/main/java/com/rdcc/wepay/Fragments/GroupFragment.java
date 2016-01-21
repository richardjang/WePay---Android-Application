package com.rdcc.wepay.Fragments;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.Parse.CloudHandler;
import com.rdcc.wepay.Cloud.User;
import com.rdcc.wepay.R;

public class GroupFragment extends Fragment implements View.OnClickListener {
    FragmentCommunicator comm;
    CloudHandler cloud;
    Button join,add,admin,treas,viewmems;
    LinearLayout joinedArea;
    TextView memNum;
    Data data;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_groupview, container, false);
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
        funds.setText("Funds: $" + String.format("%.2f", data.getGroupFunds()));
        memNum = (TextView) getActivity().findViewById(R.id.numberofMembers);
        memNum.setText("Number of Members: " + cloud.getNumberOfMembersInGroup(data.getGroupID()));
        final TextView groupdesc = (TextView) getActivity().findViewById(R.id.groupDescription);
        groupdesc.setText(data.getDescription());
        viewmems = (Button) getActivity().findViewById(R.id.viewmems);
        viewmems.setOnClickListener(this);

        joinedArea = (LinearLayout) getActivity().findViewById(R.id.joinedArea);
        join = (Button) getActivity().findViewById(R.id.join);
        join.setOnClickListener(this);
        add = (Button) getActivity().findViewById(R.id.addfunds);
        add.setOnClickListener(this);
        if(cloud.doesUserhaveaGroup(user.getID(), data.getGroupID())){
            join.setVisibility(View.GONE);
            joinedArea.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
        }

        admin = (Button) getActivity().findViewById(R.id.admin);
        admin.setOnClickListener(this);
        if(data.getAdminID().equals(user.getID())){
            admin.setVisibility(View.VISIBLE);
        }

        treas = (Button) getActivity().findViewById(R.id.treasurer);
        treas.setOnClickListener(this);
        if(data.getTreasurerID().equals(user.getID())){
            treas.setVisibility(View.VISIBLE);
        }


    }

    //Buttons will be Join group, Add funds, Admin, Treasurer, View members
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.join: //join group
                //make user part of group and show the add funds button
                cloud.storeUserandGroupData(user.getID(), data.getGroupID());

                join.setVisibility(View.GONE);
                joinedArea.setVisibility(View.VISIBLE);
                add.setVisibility(View.VISIBLE);
                memNum.setText("Number of Members: " + cloud.getNumberOfMembersInGroup(data.getGroupID()));
                break;
            case R.id.addfunds: //add funds
                comm.response(1, 0);
                break;
            case R.id.admin: //admin settings
                comm.response(1, 1);
                break;
            case R.id.treasurer: //treasurer settings
                comm.response(1, 2);
                break;
            case R.id.viewmems: //view members
                comm.response(1, 3);
                break;
            default:
                break;
        }
    }
}
    //    FragmentCommunicator comm;
//    Data myData;
//    User userData;
//    CloudHandler cloud;
//
//    //views
//    Button joinGroup, addFunds, addPay, adminArea, treasurerArea, payNow;
//    EditText addAmount;
//    LinearLayout empty, payment, admin, treasurer;
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        comm = (FragmentCommunicator) getActivity();
//        myData = comm.retrieveData();
//        userData = comm.retrieveUser();
//        cloud = new CloudHandler();
//        if(myData != null && userData != null){
//            ImageView img = (ImageView) getActivity().findViewById(R.id.groupIcon);
//            img.setImageResource(myData.getBitmap());
//
//            TextView groupName = (TextView) getActivity().findViewById(R.id.groupName);
//            groupName.setText(myData.getName());
//
//            TextView groupID = (TextView) getActivity().findViewById(R.id.groupID);
//            groupID.setText("ID: " + myData.getGroupID());
//
//            TextView groupFunds = (TextView) getActivity().findViewById(R.id.groupFunds);
//            groupFunds.setText("Funds: " + myData.getGroupFunds());
//
//            TextView groupDesc = (TextView) getActivity().findViewById(R.id.groupDescription);
//            groupDesc.setText(myData.getDescription());
//
//            empty = (LinearLayout) getActivity().findViewById(R.id.emptyArea);
//            joinGroup = (Button) getActivity().findViewById(R.id.buttonJoin);
//            joinGroup.setOnClickListener(this);
//
//            //Add Funds Area
//            payment = (LinearLayout) getActivity().findViewById(R.id.paymentArea);
//            addFunds = (Button) getActivity().findViewById(R.id.buttonAddFunds);
//            addFunds.setOnClickListener(this);
//            addAmount = (EditText) getActivity().findViewById(R.id.addAmount);
//            addPay = (Button) getActivity().findViewById(R.id.addPay);
//            addPay.setOnClickListener(this);
//
//            //Admin Area
//            admin = (LinearLayout) getActivity().findViewById(R.id.adminArea);
//            adminArea = (Button) getActivity().findViewById(R.id.buttonAdmin);
//            adminArea.setOnClickListener(this);
//
//            //Treasurer Area
//            treasurer = (LinearLayout) getActivity().findViewById(R.id.treasurerArea);
//            treasurerArea = (Button) getActivity().findViewById(R.id.buttonTreasurer);
//            treasurerArea.setOnClickListener(this);
//            TextView history = (TextView) getActivity().findViewById(R.id.paymentHistory);
//            payNow = (Button) getActivity().findViewById(R.id.treasurerCashOut);
//            payNow.setOnClickListener(this);
//
//            //TODO check if user is part of group
//
//            if(cloud.doesUserhaveaGroup(myData.getGroupID(), userData.getID())){
//                joinGroup.setVisibility(View.GONE);
//                addFunds.setVisibility(View.VISIBLE);
//            }
//
//            //if part of the group, make "Join Group" Disabled and "Add Funds" Enabled
//            //if admin, make "Admin" button Enabled
//            //if treasure, make "Treasurer" button Enabled
//
//            if(userData.getID() == myData.getAdminID()){
//                adminArea.setVisibility(View.VISIBLE);
//            }
//            if(userData.getID() == myData.getTreasurerID()){
//                treasurerArea.setVisibility(View.VISIBLE);
//            }
//
//        }else Log.d("Group Fragment", "No Data");
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        return inflater.inflate(R.layout.layout_groupview, container, false);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch(v.getId()){
//            case R.id.buttonJoin:
//                //TODO User joins the group and the add funds button appears
//                cloud.storeUserandGroupData(userData.getID(), myData.getGroupID());
//
//                joinGroup.setVisibility(View.GONE);
//                addFunds.setVisibility(View.VISIBLE);
//                break;
//            case R.id.buttonAddFunds:
//                payment.setVisibility(View.VISIBLE);
//                admin.setVisibility(View.GONE);
//                treasurer.setVisibility(View.GONE);
//                empty.setVisibility(View.GONE);
//                break;
//            case R.id.addPay:
//                if(!addAmount.getText().toString().equals("")){
//                    myData.setGroupFunds(myData.getGroupFunds() + Float.parseFloat(addAmount.getText().toString()));
//                    myData.setPayHistory(myData.getPayHistory() +
//                            "\nUser: " + userData.getName() + " has made a payment of: $" + addAmount.getText().toString());
//                    //TODO update funds and payment history on database
//
//                    cloud.updateFundsviaGroupID(myData);
//                    cloud.updatePaymentHistoryViaGroupID(myData);
//
//                    Toast.makeText(getActivity(), "Thank you for your Payment", Toast.LENGTH_LONG).show();
//                    addAmount.setText("");
//                }
//                break;
//            case R.id.buttonAdmin:
//                admin.setVisibility(View.VISIBLE);
//                payment.setVisibility(View.GONE);
//                treasurer.setVisibility(View.GONE);
//                empty.setVisibility(View.GONE);
//                break;
//            case R.id.buttonTreasurer:
//                treasurer.setVisibility(View.VISIBLE);
//                payment.setVisibility(View.GONE);
//                admin.setVisibility(View.GONE);
//                empty.setVisibility(View.GONE);
//                break;
//            case R.id.treasurerCashOut:
//                myData.setPayHistory(myData.getPayHistory() +
//                        "\n~~~Treasurer: " + userData.getName() + " has taken funds worth: $" + myData.getGroupFunds());
//                userData.setFunds(userData.getFunds() + myData.getGroupFunds());
//                myData.setGroupFunds(0);
//
//                //TODO update funds for both user and group
//                cloud.updateFundsviaGroupID(myData);
//                cloud.updateFundsviaUserID(userData);
//
//
//                Toast.makeText(getActivity(), "Funds has been transferred to your account", Toast.LENGTH_LONG).show();
//                break;
//            default:
//                break;
//        }
//    }
//}
