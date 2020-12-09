package com.example.my_library.bean;

import android.app.Application;


public class DataBean extends Application {
    private String mail;
    public String getMail(){
        return this.mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
}