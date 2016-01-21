package com.rdcc.wepay.Cloud.Parse;

import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataDataBase {
    final static String database1 = "UserGroupFundsInfo";
    final static String database2 = "UserandGroupID";
    final static String database3 = "UserFundInfo";
    final static String objID = "objectId";

    public void storeGroupData(Data data) { //storing everything in the group data
        ParseObject testObject = new ParseObject(database1);

        testObject.put("Bitmap", data.getBitmap());
        testObject.put("Name", data.getName());
        testObject.put("AdminID", data.getAdminID());
        testObject.put("Description", data.getDescription());
        testObject.put("PayHistory", data.getPayHistory());
        testObject.put("TreasurerID", data.getTreasurerID());
        testObject.put("GroupFunds", data.getGroupFunds());
        testObject.saveInBackground();
    }

    public Data retrieveGroupDatabyGroupID(String GroupID) { //This function will retrieve
        Data data = new Data();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(database1);
        Log.d("Data Database", "Searching for " + GroupID);
        query.whereEqualTo(objID, GroupID);
        try {
            ParseObject object = query.getFirst();
            Log.d("Data Database", "Data found!");
            data.setBitmap(object.getInt("Bitmap"));
            data.setName(object.getString("Name"));
            data.setAdminID(object.getString("AdminID"));
            data.setDescription(object.getString("Description"));
            data.setGroupID(object.getObjectId());
            data.setGroupFunds(object.getDouble("GroupFunds"));
            data.setPayHistory(object.getString("PayHistory"));
            data.setTreasurerID(object.getString("TreasurerID"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
    public ArrayList<Data> retrieveGroupDatabyName(String name) { //This function will retrieve
        ArrayList<Data> list = new ArrayList<Data>();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(database1);
        query.whereEqualTo("Name", name);
        try {
            List<ParseObject> objects = query.find();
            for (int i = 0; i < objects.size(); i++) {
                ParseObject object = objects.get(i);
                Data data = new Data();
                data.setBitmap(object.getInt("Bitmap"));
                data.setName(object.getString("Name"));
                data.setAdminID(object.getString("AdminID"));
                data.setDescription(object.getString("Description"));
                data.setGroupID(object.getObjectId());
                data.setGroupFunds(object.getDouble("GroupFunds"));
                data.setPayHistory(object.getString("PayHistory"));
                data.setTreasurerID(object.getString("TreasurerID"));
                list.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public Data createNewGroup(Data data){
        Data newData = data;

        final ParseObject testObject = new ParseObject(database1);

        testObject.put("Bitmap", data.getBitmap());
        testObject.put("Name", data.getName());
        testObject.put("AdminID", data.getAdminID());
        testObject.put("Description", data.getDescription());
        testObject.put("PayHistory", data.getPayHistory());
        testObject.put("TreasurerID", data.getTreasurerID());
        testObject.put("GroupFunds", data.getGroupFunds());
        try{
            testObject.save();
            data.setGroupID(testObject.getObjectId());
        }catch (Exception e){
            e.printStackTrace();
        }

        return newData;
    }
    public void updateGroupNameandDescription(final Data data){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserGroupFundsInfo");

        // Retrieve the object by id
        query.getInBackground(data.getGroupID(), new GetCallback<ParseObject>() {
            public void done(ParseObject group, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    group.put("Name", data.getName());
                    group.put("Description", data.getDescription());
                    group.saveInBackground();
                }
            }
        });
    }
}

//
//public class DataDataBase {
//
//
////    private class finish {
////        private boolean fin = false;
////
////        public boolean isFin() {
////            return fin;
////        }
////
////        public void setFin(boolean fin) {
////            this.fin = fin;
////        }
////    }
//
//
//
//    public int generateUniqueGroupID() {//this function will generate a unique group ID not already exisiting in the database
//
////        final finish fin = new finish();
//
//
//        Random r = new Random();
//        int randomGroupID = 0;
////
////        while (!fin.isFin()) {
//            randomGroupID = r.nextInt();
////
////            //Checking to see if we can find it in Table 3
////            ParseQuery<ParseObject> query = ParseQuery.getQuery("UserGroupInfo");
////            query.whereEqualTo("GroupID", randomGroupID); //Under the "Email" column in the database, we will see if what the email the user entered exists
////            query.getFirstInBackground(new GetCallback<ParseObject>() {
////                public void done(ParseObject object, com.parse.ParseException e) {
////                    if (object == null) {   //if group id does not exist then we can use it
////                        fin.setFin(true);
////
////
////                    } else {
////                        fin.setFin(false);
////
////                    }
////                }
////            });
////
////        }
//
//        return randomGroupID;
//    }
//
//
//    /*
//
////    ParseObject testObject = new ParseObject("teststsetest");
////    testObject.put("foo", "bar");
////    testObject.saveInBackground();
//     */
//
//
//    public void storeGroupData(Data data) { //storing everything in the group data
//        ParseObject testObject = new ParseObject("UserGroupFundsInfo");
//
//        testObject.put("GroupID", data.getGroupID());
//        testObject.put("Bitmap", data.getBitmap());
//        testObject.put("Name", data.getName());
//        testObject.put("AdminID", data.getAdminID());
//        testObject.put("Description", data.getDescription());
//        testObject.put("PayHistory", data.getPayHistory());
//        testObject.put("TreasurerID", data.getTreasurerID());
//        testObject.put("GroupFunds", data.getGroupFunds());
//        testObject.saveInBackground();
//
//    }
//
//    public Data retrieveGroupDatabyGroupID(int GroupID) { //This function will retrieve
//        final Data data = new Data();
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserGroupFundsInfo");
//        query.whereEqualTo("GroupID", GroupID);
//        query.getFirstInBackground(new GetCallback<ParseObject>() {
//            public void done(ParseObject object, com.parse.ParseException e) {
//                if (object == null) { //check if id exists
//                    //id does not exist
//
//                } else {
//                    //id does exist
//                    //user1.setID(object.getInt("UserID"));
//                    data.setBitmap(object.getInt("Bitmap"));
//                    data.setName(object.getString("Name"));
//                    data.setAdminID(object.getInt("AdminID"));
//                    data.setDescription(object.getString("Description"));
//                    data.setGroupID(object.getInt("GroupID"));
//                    data.setGroupFunds(object.getDouble("GroupFunds"));
//                    data.setPayHistory(object.getString("PayHistory"));
//                    data.setTreasurerID(object.getInt("TreasureID"));
//                    data.setAdminID(object.getInt("AdminID"));
//
//
//                }
//            }
//        });
//
//
//        return data;
//    }
//
//    public Data retrieveGroupDatabyUserID(int UserID) { //This function will retrieve
//        final Data data = new Data();
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserGroupFundsInfo");
//        query.whereEqualTo("GroupID", UserID);
//        query.getFirstInBackground(new GetCallback<ParseObject>() {
//            public void done(ParseObject object, com.parse.ParseException e) {
//                if (object == null) { //check if id exists
//                    //id does not exist
//
//                } else {
//                    //id does exist
//                    //user1.setID(object.getInt("UserID"));
//                    data.setBitmap(object.getInt("Bitmap"));
//                    data.setName(object.getString("Name"));
//                    data.setAdminID(object.getInt("AdminID"));
//                    data.setDescription(object.getString("Description"));
//                    data.setGroupID(object.getInt("GroupID"));
//                    data.setGroupFunds(object.getDouble("GroupFunds"));
//                    data.setPayHistory(object.getString("PayHistory"));
//                    data.setTreasurerID(object.getInt("TreasureID"));
//                    data.setAdminID(object.getInt("AdminID"));
//
//
//                }
//            }
//        });
//
//
//        return data;
//    }
//
//
//
//
//}
