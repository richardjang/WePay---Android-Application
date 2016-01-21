package com.rdcc.wepay.Cloud.Group;


import com.rdcc.wepay.Cloud.Parse.CloudHandler;

import java.util.ArrayList;

public class Lab {
    private ArrayList<Data> data;
    private CloudHandler cloud;

    public Lab(){
        data = new ArrayList<Data>();
        cloud = new CloudHandler();
    }
    public ArrayList<Data> getDataList() {
        return data;
    }
    public boolean isEmpty(){
        return data.isEmpty();
    }

    public void usersLab(String userid){
        data.clear();
        data.addAll(cloud.getAllGroupsAssociatedwithUSER(userid));
    }
    public void searchLab(String useremail, String groupname){
        //"" in argument means not to search by a ID category

        //TODO clear and reparse data
            data.clear();
            if(!useremail.equals("")) {
                data.addAll(cloud.getAllGroupsAssociatedwithUSER(cloud.retrieveUserfromDBviaEmail(useremail).getID()));
            }
            if(!groupname.equals("")){
                data.addAll(cloud.retrieveGroupDatabyName(groupname));
            }
    }

    //private void clearDuplicates(){
        //iterate through the list and clear the duplicate groups
        //to save time, we aren't implementing this
    //}
}
