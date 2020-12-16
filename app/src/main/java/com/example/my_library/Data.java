package com.example.my_library;

import android.app.Application;

public class Data extends Application {

    private String mail;


    public String getMail(){
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
