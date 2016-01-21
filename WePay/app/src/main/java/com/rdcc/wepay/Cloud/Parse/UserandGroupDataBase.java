package com.rdcc.wepay.Cloud.Parse;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.User;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserandGroupDataBase {
    final static String database1 = "UserGroupFundsInfo";
    final static String database2 = "UserandGroupID";
    final static String database3 = "UserFundInfo";
    final static String objID = "objectId";

    static DataDataBase dataDataBase = new DataDataBase();

    public void storeUserandGroupData(String UserID, String GroupID) { //this will associate a user with a group.
        ParseObject testObject = new ParseObject(database2);

        testObject.put("UserID", UserID);
        testObject.put("GroupID", GroupID);
        try{
            testObject.save();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean doesUserhaveaGroup(String GroupID, String UserID) { //checking if USERID has a GroupID associated with it
        boolean hasAGroup = false;


        ParseQuery<ParseObject> query = ParseQuery.getQuery(database2);
        query.whereEqualTo("UserID", UserID);
        try {
            List<ParseObject> objects = query.find();
            for (int i = 0; i < objects.size(); i++) {
                if (objects.get(i).getString("GroupID").equals(GroupID)) hasAGroup = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasAGroup;
    }

    public ArrayList<Data> getAllGroupsAssociatedwithUSER(String UserID) { //this function will retrieve all group IDs associated with the user ID passedin
        ArrayList<Data> list = new ArrayList<Data>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(database2);
        query.whereEqualTo("UserID", UserID);
        CloudHandler cloud = new CloudHandler();
        try {
            List<ParseObject> objects = query.find();
            for (int i = 0; i < objects.size(); i++) {
                Data data = cloud.retrieveGroupDatabyGroupID(objects.get(i).getString("GroupID"));
                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<User> getAllUsersAssociatedwithGROUP(String GroupID) { //this function will retrieve all group IDs associated with the user ID passedin
        ArrayList<User> list = new ArrayList<User>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(database2);
        query.whereEqualTo("GroupID", GroupID);
        CloudHandler cloud = new CloudHandler();
        try {
            List<ParseObject> objects = query.find();
            for (int i = 0; i < objects.size(); i++) {
                User data = cloud.retrieveUserfromDB(objects.get(i).getString("UserID"));
                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getNumberOfMembersInGroup(String GroupID){
        ArrayList<User> list = new ArrayList<User>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(database2);
        query.whereEqualTo("GroupID", GroupID);
        CloudHandler cloud = new CloudHandler();
        try {
            List<ParseObject> objects = query.find();
            return objects.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    //TODO test and change
    public void updateFundsviaUserID(final User user) { //this function will update the funds associated with a user ID


        ParseQuery<ParseObject> query = ParseQuery.getQuery(database3);

        // Retrieve the object by id which in our case is the userID
        query.getInBackground(user.getID(), new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    object.put("Funds", user.getFunds());
                    object.saveInBackground();
                }
            }
        });


    }

    public void updateFundsviaGroupID(final Data group) { //this function will update the funds associated with a group ID

        ParseQuery<ParseObject> query = ParseQuery.getQuery(database1);

        // Retrieve the object by id which in our case is the userID
        query.getInBackground(group.getGroupID(), new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    object.put("GroupFunds", group.getGroupFunds());
                    object.put("PayHistory", group.getPayHistory());
                    object.saveInBackground();
                }
            }
        });


    }

}

//
//public class UserandGroupDataBase {
//
//
//    public void storeUserandGroupData(int UserID, int GroupID) { //this will associate a user with a group.
//        ParseObject testObject = new ParseObject("UserandGroupID");
//
//        testObject.put("UserID", UserID);
//        testObject.put("GroupID", GroupID);
//
//        testObject.saveInBackground();
//
//    }
//
//
//    private class userHasaGroup {
//        boolean hasAGroup = false;
//    }
//
//
//    public boolean doesUserhaveaGroup(final int GroupID, int UserID) { //checking if USERID has a GroupID associated with it
//        final userHasaGroup hasaGroup = new userHasaGroup();
//
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserandGroupID");
//        query.whereEqualTo("UserID", UserID);
//        query.getFirstInBackground(new GetCallback<ParseObject>() {
//            public void done(ParseObject object, com.parse.ParseException e) {
//                if (object != null) {   //if we're able to find the user ID
//
//                    int groupIDfound = object.getInt("GroupID"); //the group ID associated with the user
//
//                    if (groupIDfound == GroupID) { //the user is associated with this group
//                        hasaGroup.hasAGroup = true;
//                    } else { //the user is not associated with this group.
//                        hasaGroup.hasAGroup = false;
//                    }
//
//
//                }
//            }
//        });
//
//
//        return hasaGroup.hasAGroup;
//    }
//
//
//    class Holder<T> {
//        private ArrayList<T> value;
//
//        Holder(ArrayList<T> value) {
//            setValue(value);
//        }
//
//        ArrayList<T> getValue() {
//            return value;
//        }
//
//        void setValue(ArrayList<T> value) {
//            this.value = value;
//        }
//    }
//
//    public ArrayList<Data> getAllGroupIDAssociatedwithUSER(int UserID){ //this function will retrieve all group IDs associated with the user ID passedin
//
//        final Holder<Data> holder= new Holder<Data>(new ArrayList<Data>());
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserGroupFundsInfo");
//        query.whereEqualTo("GroupID", 1);//looking for a value under UserID equal to userID
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    for (Iterator<ParseObject> iter = objects.iterator(); iter.hasNext(); ) {
//                        ParseObject object = iter.next();
//                        Data data = new Data();
//                        data.setBitmap(object.getInt("Bitmap"));
//                        data.setName(object.getString("Name"));
//                        Log.d("YES", "" + data.getName());
//                        data.setAdminID(object.getInt("AdminID"));
//                        data.setDescription(object.getString("Description"));
//                        data.setGroupID(object.getInt("GroupID"));
//                        data.setGroupFunds(object.getDouble("GroupFunds"));
//                        data.setPayHistory(object.getString("PayHistory"));
//                        data.setTreasurerID(object.getInt("TreasureID"));
//                        data.setAdminID(object.getInt("AdminID"));
//                        ArrayList<Data> list = holder.getValue();
//                        list.add(data);
//                        holder.setValue(list);
//                    }
//                } else {
//                    //failed
//                }
//                Log.d("YES", holder.getValue().get(0).getName());
//            }
//        });
//        Log.d("YES", holder.getValue().get(0).getName());
//        return holder.getValue();
//    }
//
//}
