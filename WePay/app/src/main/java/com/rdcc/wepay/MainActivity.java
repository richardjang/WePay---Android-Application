package com.rdcc.wepay;

//This class is the Split View Controller
//It will handle a;; the fragments that will show up on the Main Activity

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.rdcc.wepay.Cloud.Group.Data;
import com.rdcc.wepay.Cloud.Group.Lab;
import com.rdcc.wepay.Cloud.Parse.CloudHandler;
import com.rdcc.wepay.Cloud.User;
import com.rdcc.wepay.Fragments.FragmentCommunicator;
import com.rdcc.wepay.Fragments.GroupFragment;
import com.rdcc.wepay.Fragments.GroupViewAddFundsFragment;
import com.rdcc.wepay.Fragments.GroupViewAdminFragment;
import com.rdcc.wepay.Fragments.GroupViewMembersListFragment;
import com.rdcc.wepay.Fragments.GroupViewTreasurerFragment;
import com.rdcc.wepay.Fragments.fragmentIDs;
import com.rdcc.wepay.Fragments.GroupListFragment;
import com.rdcc.wepay.Fragments.NewGroupFragment;
import com.rdcc.wepay.Fragments.SearchFragment;
import com.rdcc.wepay.Fragments.SettingsFragment;
import com.rdcc.wepay.Fragments.WelcomeFragment;
import com.rdcc.wepay.Fragments.loadingFragment;

import java.util.ArrayList;

public class MainActivity extends Activity implements FragmentCommunicator, View.OnClickListener {
    String debugTag = "Main Activity";

    FragmentManager fm;
    private CloudHandler cloud;

    Data dataToView; //stores a Data instance that will be used by groupView
    User userData;
    boolean canGoBack = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cloud = new CloudHandler();
        //retrieve user data from login screen activity through the bundle
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                String email = bundle.getString("Email");
                Log.d("Email Retrieved", email);
                userData = cloud.retrieveUserfromDBviaEmail(email);
            } else {
                Toast.makeText(this, "Error: User Corrupted, Don't Continue", Toast.LENGTH_LONG).show();
            }
        }
        //initialize
        initialize();
    }

    private void initialize() {
        //we need to put some fragments into our view
        setContentView(R.layout.layout_main_port);
        load();

        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //Our first view should be the newsfeed
        WelcomeFragment welcome = new WelcomeFragment();
        ft.add(R.id.winPort, welcome, "Welcome");
        ft.commit();

        //initialize our navbar buttons
        Button news = (Button) findViewById(R.id.newsPort);
        news.setOnClickListener(this);
        Button add = (Button) findViewById(R.id.addPort);
        add.setOnClickListener(this);
        Button search = (Button) findViewById(R.id.searchPort);
        search.setOnClickListener(this);
        Button settings = (Button) findViewById(R.id.settingsPort);
        settings.setOnClickListener(this);
    }

    @Override
    public void response(int fragment, int data) {
        switch (fragment) {
            case 0: //open group view
                load();
                Log.d(debugTag, "Group entered from value: " + data);
                canGoBack = false;
                fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                GroupFragment gf = new GroupFragment();
                ft.replace(R.id.winPort, gf, "Group View");
                ft.commit();
                break;
            case 1:
                canGoBack = true;
                groupViewViews(data);
                break;
            default:
                break;
        }
    }

    public void groupViewViews(int data) {
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch (data) {
            case 0: //add funds
                GroupViewAddFundsFragment addfunds = new GroupViewAddFundsFragment();
                ft.replace(R.id.winPort, addfunds, "Add Funds");
                break;
            case 1: //admin
                GroupViewAdminFragment admin = new GroupViewAdminFragment();
                ft.replace(R.id.winPort, admin, "Admin");
                break;
            case 2: //treasurer
                GroupViewTreasurerFragment treasurer = new GroupViewTreasurerFragment();
                ft.replace(R.id.winPort, treasurer, "Treasurer");
                break;
            case 3: //view members
                GroupViewMembersListFragment viewmems = new GroupViewMembersListFragment();
                ft.replace(R.id.winPort, viewmems, "View Members");
                break;
            default:
                break;
        }
        ft.commit();
    }

    //this is for our nav bar at the bottom
    @Override
    public void onClick(View v) {
        canGoBack = false;
        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch (v.getId()) {
            case R.id.newsPort:
                load();
                GroupListFragment glf = (GroupListFragment) fm.findFragmentByTag("Group List");
                if (glf == null) glf = new GroupListFragment();
                ft.replace(R.id.winPort, glf, "Group List");
                break;
            case R.id.addPort:
                NewGroupFragment ngf = (NewGroupFragment) fm.findFragmentByTag("New Group");
                if (ngf == null) ngf = new NewGroupFragment();
                ft.replace(R.id.winPort, ngf, "New Group");
                break;
            case R.id.searchPort:
                SearchFragment srchf = (SearchFragment) fm.findFragmentByTag("Search");
                if (srchf == null) srchf = new SearchFragment();
                ft.replace(R.id.winPort, srchf, "Search");
                break;
            case R.id.settingsPort:
                SettingsFragment setf = (SettingsFragment) fm.findFragmentByTag("Settings");
                if (setf == null) setf = new SettingsFragment();
                ft.replace(R.id.winPort, setf, "Settings");
                break;
            default:
                break;
        }
        ft.commit();
    }

    @Override
    public void setData(Data data) {
        dataToView = data;
    }

    @Override
    public Data retrieveData() {
        return dataToView;
    }

    @Override
    public User retrieveUser() {
        return userData;
    }

    @Override
    public void setUser(User user) {
        userData = user;
    }

    @Override
    public void load() {
        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        //  onLoginSuccess(); //this will display to the user that they're successfully logged in
                        //     onLoginFailed(); //this will display to the user that they failed to log in.
                        progressDialog.dismiss();
                    }
                }, 1500);
    }

    @Override
    public void onBackPressed() {
        if (canGoBack) {
            response(0, 1);
        }
    }
}
//public class MainActivity extends Activity implements FragmentCommunicator, View.OnClickListener{
//    String debugTag = "Main Activity";
//
//    boolean orientPortrait;
//    FragmentManager fm;
//    private CloudHandler cloud;
//
//    Data dataToView; //stores a Data instance that will be used by groupView
//    User userData;
//    Lab groupLab[] = new Lab [2];
//
//    fragmentIDs win1; //keep track of what fragment is in win1
//    fragmentIDs win4; //keep track of what frament is in win4
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        cloud = new CloudHandler();
//        //retrieve user data from login screen activity through the bundle
//        if(savedInstanceState == null) {
//            Bundle bundle = getIntent().getExtras();
//            if (bundle != null) {
//                String email = bundle.getString("Email");
//                Log.d("Email Retrieved", email);
//                userData = cloud.retrieveUserfromDBviaEmail(email);
//            } else {
//                Toast.makeText(this, "Error: User Corrupted, Don't Continue", Toast.LENGTH_LONG).show();
//            }
//        }
//        //initialize
//        setContentView(R.layout.layout_splash);
//        CloudHandler cloud = new CloudHandler();
////        for(int i=0; i<5; i++) {
////            Data newData = new Data(0, "Test Group", 0, "Testing Groups", userData.getID(), userData.getID(), "");
////            Data finalData = cloud.createNewGroup(newData);
////        }
//        ArrayList<Data> data = cloud.getAllGroupsAssociatedwithUSER(userData.getID());
//
////        initialize();
//    }
//
//    private void initialize(){
//        //we need to put some fragments into our view
//        fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//
//        //screen orientation check
//        Display display = getWindowManager().getDefaultDisplay();
//        int orient = display.getRotation();
//        if(Surface.ROTATION_0 == orient || Surface.ROTATION_180 == orient){
//            //orientation is portrait
//            orientPortrait = true;
//            setContentView(R.layout.layout_main_port);
//
////            //loading group list
////            loadingFragment lf = (loadingFragment) fm.findFragmentByTag("P Loading");
////            if(lf == null) lf = new loadingFragment();
////            ft.replace(R.id.winPort, lf, "P Loading");
////            ft.commit();
//
//            //show group list only
//            GroupListFragment glf = (GroupListFragment) fm.findFragmentByTag("P Group List Fragment");
//            if(glf == null) glf = new GroupListFragment();
//            ft.replace(R.id.winPort, glf, "P Group List Fragment");
//            win1 = fragmentIDs.GroupList;
//
//            Button news = (Button) findViewById(R.id.newsPort);
//            news.setOnClickListener(this);
//
//            Button add = (Button) findViewById(R.id.addPort);
//            add.setOnClickListener(this);
//
//            Button search = (Button) findViewById(R.id.searchPort);
//            search.setOnClickListener(this);
//
//            Button settings = (Button) findViewById(R.id.settingsPort);
//            settings.setOnClickListener(this);
//        }else {
//            //orientation is landscape
//            orientPortrait = false;
//            setContentView(R.layout.layout_main_land);
//
////            //loading group list
////            loadingFragment lf = (loadingFragment) fm.findFragmentByTag("L Loading");
////            if(lf == null) lf = new loadingFragment();
////            ft.replace(R.id.win1, lf, "L Loading");
////            ft.commit();
//
//            Button news = (Button) findViewById(R.id.newsLand);
//            news.setOnClickListener(this);
//
//            Button add = (Button) findViewById(R.id.addLand);
//            add.setOnClickListener(this);
//
//            Button search = (Button) findViewById(R.id.searchLand);
//            search.setOnClickListener(this);
//
//            Button settings = (Button) findViewById(R.id.settingsLand);
//            settings.setOnClickListener(this);
//
//            //show group list and welcome screen
//            GroupListFragment glf = (GroupListFragment) fm.findFragmentByTag("L Group List Fragment");
//            if(glf == null) glf = new GroupListFragment();
//
//            WelcomeFragment wf = (WelcomeFragment) fm.findFragmentByTag("L Welcome Fragment");
//            if(wf == null) wf = new WelcomeFragment();
//
////            ft.replace(R.id.win1, glf, "L Group List Fragment");
////            ft.commit();
////            win1 = fragmentIDs.GroupList;
////
////            ft = fm.beginTransaction();
////            ft.replace(R.id.win4, wf, "L Welcome Fragment");
////            win4 = fragmentIDs.Welcome;
//        }
//        ft.commit();
//        //groupLab[0].updateLab(userData.getID(), "");
//    }
//
//    @Override
//    public void response(int fragment, int data) {
//        if(orientPortrait){
//            portraitResponse(fragment, data);
//        }else{
//            landscapeResponse(fragment, data);
//        }
//    }
//    private void portraitResponse(int fragment, int data){
//        //We assume that the response is just to look at group view
////        switch(fragment){
////            case 1: //newgroup fragment is done and wants to open up the groupview
//                fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                GroupFragment gf = new GroupFragment();
//                ft.replace(R.id.winPort, gf, "P Group View Fragment");
//                win1 = fragmentIDs.GroupView;
//                ft.commit();
////                break;
////        }
//    }
//    private void landscapeResponse(int fragment, int data){
//        //We assume that the response is just to look at group view
//        //switch(fragment){
//        //    case 1: //newgroup fragment is done and wants to open up the groupview
//                fm = getFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                GroupFragment gf = new GroupFragment();
////                ft.replace(R.id.win4, gf, "L Group View Fragment");
//                win4 = fragmentIDs.GroupView;
//                ft.commit();
//        //      break;
//        //}
//    }
//
//    @Override
//    public void setData(Data data) {
//        dataToView = data;
//    }
//
//    @Override
//    public void setLab(Lab lab, int index) {
//        groupLab[index] = lab;
//    }
//
//    @Override
//    public Data retrieveData() {
//        return dataToView;
//    }
//
//    @Override
//    public User retrieveUser() {
//        return userData;
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        initialize();
//    }
//
//    @Override
//    public void onClick(View v) {
//        fm = getFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//
//        //this is for our nav bar at the bottom
//        switch(v.getId()){
//            case R.id.newsLand:
//                groupLab[0].updateLab(userData.getID(), "");
//                if(win1 != fragmentIDs.GroupList){
////                    loadingFragment lf = (loadingFragment) fm.findFragmentByTag("L Loading");
////                    if(lf == null) lf = new loadingFragment();
////                    ft.replace(R.id.win1, lf, "L Loading");
////                    ft.commit();
//
//                    GroupListFragment glf = (GroupListFragment) fm.findFragmentByTag("L Group List Fragment");
//                    if(glf == null) glf = new GroupListFragment();
////                    ft.replace(R.id.win1, glf, "L Group List Fragment");
//                    win1 = fragmentIDs.GroupList;
//                }
//                break;
//            case R.id.newsPort:
//                groupLab[0].updateLab(userData.getID(), "");
//                if(win1 != fragmentIDs.GroupList){
////                    loadingFragment lf = (loadingFragment) fm.findFragmentByTag("P Loading");
////                    if(lf == null) lf = new loadingFragment();
////                    ft.replace(R.id.winPort, lf, "P Loading");
////                    ft.commit();
//
//                    GroupListFragment glf = (GroupListFragment) fm.findFragmentByTag("P Group List Fragment");
//                    if(glf == null) glf = new GroupListFragment();
//                    ft.replace(R.id.winPort, glf, "P Group List Fragment");
//                    win1 = fragmentIDs.GroupList;
//                }
//                break;
//            case R.id.addLand:
//                if(win4 != fragmentIDs.NewGroup){
//                    NewGroupFragment ngf = (NewGroupFragment) fm.findFragmentByTag("L New Group Fragment");
//                    if(ngf == null) ngf = new NewGroupFragment();
////                    ft.replace(R.id.win4, ngf, "L New Group Fragment");
//                    win4 = fragmentIDs.NewGroup;
//                }
//                break;
//            case R.id.addPort:
//                if(win1 != fragmentIDs.NewGroup){
//                    NewGroupFragment ngf = (NewGroupFragment) fm.findFragmentByTag("P New Group Fragment");
//                    if(ngf == null) ngf = new NewGroupFragment();
//                    ft.replace(R.id.winPort, ngf, "P New Group Fragment");
//                    win1 = fragmentIDs.NewGroup;
//                }
//                break;
//            case R.id.searchLand:
//                if(win4 != fragmentIDs.GroupSearch){
//                    SearchFragment sf = (SearchFragment) fm.findFragmentByTag("L Search Fragment");
//                    if(sf == null) sf = new SearchFragment();
////                    ft.replace(R.id.win1, sf, "L Search Fragment");
//                    win4 = fragmentIDs.GroupSearch;
//                }
//                break;
//            case R.id.searchPort:
//                if(win1 != fragmentIDs.GroupSearch){
//                    SearchFragment sf = (SearchFragment) fm.findFragmentByTag("P Search Fragment");
//                    if(sf == null) sf = new SearchFragment();
//                    ft.replace(R.id.winPort, sf, "P Search Fragment");
//                    win1 = fragmentIDs.GroupSearch;
//                }
//                break;
//            case R.id.settingsLand:
//                if(win4 != fragmentIDs.Settings){
//                    SettingsFragment sf = (SettingsFragment) fm.findFragmentByTag("L Settings Fragment");
//                    if(sf == null) sf = new SettingsFragment();
////                    ft.replace(R.id.win4, sf, "L Settings Fragment");
//                    win4 = fragmentIDs.Settings;
//                }
//                break;
//            case R.id.settingsPort:
//                if(win1 != fragmentIDs.Settings){
//                    SettingsFragment sf = (SettingsFragment) fm.findFragmentByTag("P Settings Fragment");
//                    if(sf == null) sf = new SettingsFragment();
//                    ft.replace(R.id.winPort, sf, "P Settings Fragment");
//                    win1 = fragmentIDs.Settings;
//                }
//                break;
//            default:
//                break;
//        }
//        ft.commit();
//    }
//
//    @Override
//    public void onBackPressed() {
//        //theres no back button functionality anymore
//    }
//}
