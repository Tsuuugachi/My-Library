package com.example.my_library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        setContentView(R.layout.mypage);
        setContentView(R.layout.new_registration);
        setContentView(R.layout.login);
        setContentView(R.layout.user_followlist);
        setContentView(R.layout.user_follower);
        setContentView(R.layout.bookshelf);
        setContentView(R.layout.read_list_ranking);
        setContentView(R.layout.user_follower);
        setContentView(R.layout.user_read_listdetails);
        setContentView(R.layout.new_registration_verification);
        setContentView(R.layout.barcode_reading);
        setContentView(R.layout.user_read_listdetails);
        setContentView(R.layout.details);
        setContentView(R.layout.favorite_book);
        setContentView(R.layout.search_result);
        setContentView(R.layout.myread_list);
        setContentView(R.layout.user_info_change);
        setContentView(R.layout.withdrawal_verification);
        setContentView(R.layout.myread_list_details);
    }

    //SearchView追加

}
