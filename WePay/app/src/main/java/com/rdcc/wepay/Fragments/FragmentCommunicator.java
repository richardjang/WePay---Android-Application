package com.rdcc.wepay.Fragments;

import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.Group.Lab;
import com.rdcc.wepay.Cloud.User;

public interface FragmentCommunicator {
    public void response(int fragment, int data);
    //convention: fragment trying to say something to the main activity
    //data is what the fragment tells the main activity to do

    public void setData(Data data);
    public Data retrieveData();

    public void setUser(User user);
    public User retrieveUser();

    public void load();
}