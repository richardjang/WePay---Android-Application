package com.rdcc.wepay.Cloud.Parse;

import android.content.SharedPreferences;
import android.util.Log;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rdcc.wepay.Cloud.User;

import java.util.ArrayList;
import java.util.Random;


public class UserDataBase {
    final static String database1 = "UserGroupFundsInfo";
    final static String database2 = "UserandGroupID";
    final static String database3 = "UserFundInfo";
    final static String objID = "objectId";

    public void storeUserinDB(User user) { //storing everything

        ParseObject testObject = new ParseObject(database3);
        testObject.put("LoginID", user.getID()); //object id of the user login
        testObject.put("Name", user.getName());
        testObject.put("bitmap", user.getBitmap());
        testObject.put("Funds", user.getFunds());
        testObject.put("Email", user.getEmail());

        testObject.saveInBackground();
    }

    public User retrieveUserfromDB(String id) { //retrieve everything

        User user1 = new User();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(database3);
        query.whereEqualTo("LoginID", id);
        try{
            ParseObject object = query.getFirst();
                    user1.setID(id);
                    user1.setName(object.getString("Name"));
            //We only have one bitmap anyways...
//                    user1.setBitmap(object.getInt("bitmap"));
                    user1.setFunds(object.getDouble("Funds"));
                    user1.setEmail(object.getString("Email"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return user1;
    }
    public User retrieveUserfromDBviaEmail(String email) { //retrieve everything

        User user1 = new User();
        ParseQuery<ParseObject> query = ParseQuery.getQuery(database3);
        query.whereEqualTo("Email", email);
        try{
            ParseObject object = query.getFirst();
            user1.setID(object.getString("LoginID"));
            user1.setName(object.getString("Name"));
            //We only have one bitmap anyways...
//                    user1.setBitmap(object.getInt("bitmap"));
            user1.setFunds(object.getDouble("Funds"));
            user1.setEmail(object.getString("Email"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return user1;
    }
}

//
//public class UserDataBase {
//
//
//
//    public boolean isUserPartofGroup(int UserID, int GroupID){
//
//
//        return true;
//    }
//
//z
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
//    public int generateUniqueUserID() {//this function will generate a unique user ID not already exisiting in the database
//
////        final finish fin = new finish();
//
//
//        Random r = new Random();
//        int randomUserID = 0;
//
//        //while (!fin.isFin()) {
//            randomUserID = r.nextInt();
////
////            //Checking to see if we can find it in Table 3
////            ParseQuery<ParseObject> query = ParseQuery.getQuery("UserFundInfo");
////            query.whereEqualTo("UserID", randomUserID); //Under the "Email" column in the database, we will see if what the email the user entered exists
////            query.getFirstInBackground(new GetCallback<ParseObject>() {
////                public void done(ParseObject object, com.parse.ParseException e) {
////                    if (object == null) {   //if group id does not exist then we can use it
////                        Log.d("WHY", "YES");
////                        fin.setFin(true);
////
////                    } else {//we can find it
////                        Log.d("WHY", "NO");
////                        fin.setFin(true);
////                    }
////                }
////            });
////            if(fin.isFin()) Log.d("YES", "YES");
////        //}
//
//        return randomUserID;
//    }
//
//
//    public void storeDatainDB(User user) { //storing everything
//
//        ParseObject testObject = new ParseObject("UserFundInfo");
//        testObject.put("UserID", user.getID());
//        testObject.put("Name", user.getName());
//        testObject.put("bitmap", user.getBitmap());
//        testObject.put("Funds", user.getFunds());
//
//        testObject.saveInBackground();
//
//
//
//    }
//
//
//    public static class Holder<T> {
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
//    public Holder<User> userHolder= new Holder<User>(new ArrayList<User>());
//    public void addData(User data){
//        ArrayList<User> list = userHolder.getValue();
//        list.add(data);
//        userHolder.setValue(list);
//    }
//
//
//    public User retrieveUserfromDB(int id) { //retrieve everything
//        //final User user1 = new User();\
//
//        //final Holder<User> userHolder= new Holder<User>(new ArrayList<User>());
//
//
//        User user1 = new User();
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserFundInfo");
//        query.whereEqualTo("UserID", id);
//        try{
//            ParseObject object = query.getFirst();
//                    user1.setID(object.getInt("UserID"));
//                    user1.setName(object.getString("Name"));
//                    user1.setBitmap(object.getInt("bitmap"));
//                    user1.setFunds(object.getDouble("Funds"));
//        }catch(Exception e){
//
//        }
//
//
////        query.getFirstInBackground(new GetCallback<ParseObject>() {
////            Holder<User> holder = UserDataBase.this.userHolder;
////            public void done(ParseObject object, com.parse.ParseException e) {
////                if (object == null) { //check if id exists
////                    //id does not exist
////
////                } else {
////                    //id does exist
////                    Log.d("USER", "FOUND!");
////                    User user1 = new User();
////                    user1.setID(object.getInt("UserID"));
////                    user1.setName(object.getString("Name"));
////                    user1.setBitmap(object.getInt("bitmap"));
////                    user1.setFunds(object.getDouble("Funds"));
//////                    ArrayList<User> list = userHolder.getValue();
//////                    Holder<User> holder = UserDataBase.this.userHolder;
////
//////                        ArrayList<User> list = holder.getValue();
//////                        list.add(user1);
//////                        holder.setValue(list);
////                    UserDataBase.this.addData(user1);
////
//////                    userHolder.setValue(list);
//////                    setUser(object.getInt("UserID"), object.getString("Name"), object.getInt("bitmap"),object.getDouble("Funds"));
////
////                }
////            }
////        });
//
////        return new User();
//        return user1;
//    }
//
//
//}
