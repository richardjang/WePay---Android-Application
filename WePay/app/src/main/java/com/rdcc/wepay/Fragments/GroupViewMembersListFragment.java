package com.rdcc.wepay.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rdcc.wepay.Cloud.Group.Adapter;
import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.Parse.CloudHandler;
import com.rdcc.wepay.Cloud.User;
import com.rdcc.wepay.Cloud.UserAdapter;
import com.rdcc.wepay.R;

import java.util.ArrayList;

public class GroupViewMembersListFragment extends Fragment {
    FragmentCommunicator comm;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (FragmentCommunicator) getActivity();
        //make ListView and Populate it
        CloudHandler cloud = new CloudHandler();
        ListView myList = (ListView) getActivity().findViewById(R.id.grouplistview);
        TextView title = (TextView) getActivity().findViewById(R.id.ListTitle);
        title.setText("Member List");
        ArrayList<User> list = cloud.getAllUsersAssociatedwithGROUP(comm.retrieveData().getGroupID());
        UserAdapter adapter = new UserAdapter(getActivity(), list);
        myList.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.layout_grouplist, container, false);
    }
}