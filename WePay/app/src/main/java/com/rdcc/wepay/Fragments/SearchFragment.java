package com.rdcc.wepay.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.rdcc.wepay.Cloud.Group.Adapter;
import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.Group.Lab;
import com.rdcc.wepay.R;

public class SearchFragment extends Fragment{
    FragmentCommunicator comm;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Searching...");

        comm = (FragmentCommunicator) getActivity();
        final EditText searcher = (EditText) getActivity().findViewById(R.id.searchBar);
        Button searchButt = (Button) getActivity().findViewById(R.id.searchButton);

        ListView results = (ListView) getActivity().findViewById(R.id.searchList);
        final Lab groupLab = new Lab();
        final Adapter adapter = new Adapter(getActivity(), groupLab.getDataList());
        results.setAdapter(adapter);
        results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Data data = (Data) parent.getItemAtPosition(position);

                comm.setData(data);
                comm.response(0,0);
            }
        });

        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comm.load();
                groupLab.searchLab(searcher.getText().toString(), searcher.getText().toString());
                adapter.notifyDataSetChanged();
                if(groupLab.isEmpty()){
                    Toast.makeText(getActivity(), "Unable to find Data", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        return inflater.inflate(R.layout.layout_searchview, container, false);
    }

}
