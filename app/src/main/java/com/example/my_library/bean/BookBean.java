package com.example.my_library.bean;

import android.app.Application;

public class BookBean extends Application {
    private String book_name;
    private String myread_list;
    private String book_image;

    public String getBook_name(){
        return this.book_name;
    }
    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getMyread_list(){
        return this.myread_list;
    }
    public void setMyread_list(String myread_list) {
        this.myread_list = myread_list;
    }

    public String getBook_image(){
        return this.book_image;
    }
    public void setBook_image(String book_image) {
        this.book_image = book_image;
    }
}
