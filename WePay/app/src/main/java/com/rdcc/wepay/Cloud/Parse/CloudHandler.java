package com.rdcc.wepay.Cloud.Parse;

//Main class that handles all our cloud services
//Therfore, we need more subclasses interface with the Google Cloud Platform

import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.User;

import java.util.ArrayList;

public class CloudHandler {
    private static UserandGroupDataBase ugdb = new UserandGroupDataBase();
    private static UserDataBase udb = new UserDataBase();
    private static DataDataBase ddb = new DataDataBase();

    //DataDataBase
    public void storeGroupData(Data data) {
        ddb.storeGroupData(data);
    }

    public Data retrieveGroupDatabyGroupID(String GroupID) {
        return ddb.retrieveGroupDatabyGroupID(GroupID);
    }

    public Data createNewGroup(Data data) {
        Data myData = ddb.createNewGroup(data);
        ugdb.storeUserandGroupData(data.getAdminID(), data.getGroupID());
        return data;
    }

    public ArrayList<Data> retrieveGroupDatabyName(String name) {
        return ddb.retrieveGroupDatabyName(name);
    }

    public void updateGroupNameandDescription(final Data data) {
        ddb.updateGroupNameandDescription(data);
    }

    //UserDataBase
    public void storeUserinDB(User user) {
        udb.storeUserinDB(user);
    }

    public User retrieveUserfromDB(String id) {
        return udb.retrieveUserfromDB(id);
    }

    public User retrieveUserfromDBviaEmail(String email) {
        return udb.retrieveUserfromDBviaEmail(email);
    }

    //UserandGroupDataBase
    public void storeUserandGroupData(String UserID, String GroupID) {
        ugdb.storeUserandGroupData(UserID, GroupID);
    }

    public boolean doesUserhaveaGroup(String UserID, String GroupID) {
        return ugdb.doesUserhaveaGroup(GroupID, UserID);
    }

    public ArrayList<Data> getAllGroupsAssociatedwithUSER(String UserID) {
        return ugdb.getAllGroupsAssociatedwithUSER(UserID);
    }

    public ArrayList<User> getAllUsersAssociatedwithGROUP(String GroupID) {
        return ugdb.getAllUsersAssociatedwithGROUP(GroupID);
    }

    public int getNumberOfMembersInGroup(String GroupID) {
        return ugdb.getNumberOfMembersInGroup(GroupID);
    }

    public void updateFundsviaUserID(final User user) {
        ugdb.updateFundsviaUserID(user);
    }

    public void updateFundsviaGroupID(final Data group) {
        ugdb.updateFundsviaGroupID(group);
    }

}
