package com.rdcc.wepay.Fragments;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.Parse.CloudHandler;
import com.rdcc.wepay.R;

public class GroupViewAdminFragment extends Fragment{
    FragmentCommunicator comm;
    Data data;
    CloudHandler cloud;
    Button modifyGroup;
    EditText name, desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_admin, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comm = (FragmentCommunicator) getActivity();
        data = comm.retrieveData();
        cloud = new CloudHandler();
        ImageView image = (ImageView) getActivity().findViewById(R.id.groupIcon);
        image.setImageResource(data.getDrawable());

        modifyGroup = (Button) getActivity().findViewById(R.id.adminMod);
//        deleteGroup = (Button) getActivity().findViewById(R.id.adminDelete);
        name = (EditText) getActivity().findViewById(R.id.groupName);

        desc = (EditText) getActivity().findViewById(R.id.groupDescription);

        modifyGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(desc.getText().toString().equals("") && name.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please modify something before pressing Modify", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!desc.getText().toString().equals("")){
                    data.setDescription(desc.getText().toString());
                }
                if(!name.getText().toString().equals("")){
                    data.setName(name.getText().toString());
                }
                cloud.updateGroupNameandDescription(data);
                Toast.makeText(getActivity(), "Group Modified!", Toast.LENGTH_SHORT).show();
                comm.response(0,2);
            }
        });
    }
}
