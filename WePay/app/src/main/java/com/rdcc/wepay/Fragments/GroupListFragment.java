package com.rdcc.wepay.Fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rdcc.wepay.Cloud.Group.Adapter;
import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.Group.Lab;
import com.rdcc.wepay.R;

public class GroupListFragment extends Fragment {
    FragmentCommunicator comm;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (FragmentCommunicator) getActivity();

        //make ListView and Populate it
        ListView myList = (ListView) getActivity().findViewById(R.id.grouplistview);
        Lab groupLab = new Lab();
        groupLab.usersLab(comm.retrieveUser().getID());
        Adapter adapter = new Adapter(getActivity(), groupLab.getDataList());
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Data data = (Data) parent.getItemAtPosition(position);

                comm.setData(data);
                comm.response(0,0);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.layout_grouplist, container, false);
    }
}
