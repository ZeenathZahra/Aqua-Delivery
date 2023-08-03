package com.android.aquadelivery;

import android.widget.EditText;

public class User {

    public String userName;
    public String email;
    public String address;
    public String contactNumber;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public User(){

    }
    public User(String userName, String email, String address, String contactNumber ){
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.contactNumber = contactNumber;
    }

}
