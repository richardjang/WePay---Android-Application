package com.rdcc.wepay.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.rdcc.wepay.Cloud.Group.Bitmaps;
import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.Parse.CloudHandler;
import com.rdcc.wepay.R;

public class NewGroupFragment extends Fragment{
    int iconSelected = 0;
    FragmentCommunicator comm;
    Bitmaps bm = new Bitmaps();
    private CloudHandler cloud;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        final EditText name = (EditText) getActivity().findViewById(R.id.newName);
        final EditText desc = (EditText) getActivity().findViewById(R.id.newDesc);
        cloud = new CloudHandler();
        RadioGroup radio = (RadioGroup) getActivity().findViewById(R.id.newRadioGroup);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.newIcon1:
                        iconSelected = 1;
                        break;
                    case R.id.newIcon2:
                        iconSelected = 2;
                        break;
                    case R.id.newIcon3:
                        iconSelected = 3;
                        break;
                }
            }
        });

        comm = (FragmentCommunicator) getActivity();
        Button create = (Button) getActivity().findViewById(R.id.newMake);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString = name.getText().toString();
                String descString = desc.getText().toString();
                String admin = comm.retrieveUser().getID();
                String treasurer = comm.retrieveUser().getID();
                //remember to have the user placed into contributors list
                //we also have int iconSelected
                int groupFunds = 0;

                //TODO make this new group as data in our database
                //we are now done with making a new group, signal to the main activity that we finished
                Data newGroupData = new Data(iconSelected, nameString, groupFunds, descString, admin, treasurer, "");
                Data finalData = cloud.createNewGroup(newGroupData);
                comm.setData(finalData);
                comm.response(0,0);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.layout_newgroup, container, false);
    }
}
