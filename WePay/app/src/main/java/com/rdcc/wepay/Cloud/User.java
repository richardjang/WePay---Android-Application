package com.rdcc.wepay.Cloud;


import com.rdcc.wepay.Cloud.Group.Bitmaps;

public class User {

    private String ID;
    private String name;
    private int bitmap;
    private double funds;
    private Bitmaps bm = new Bitmaps();
    private String email;

    public User(){
        ID = "";
        name = "User";
        funds = 0;
        bitmap = 4;
    }

    public int getBitmap() {
        return bitmap;
    }
    public int getDrawable(){
        return bm.conversion(bitmap);
    }
    public void setBitmap(int bitmap) {
        this.bitmap = bitmap;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
