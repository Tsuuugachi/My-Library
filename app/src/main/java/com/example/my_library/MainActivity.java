package com.example.my_library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;



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
        setContentView(R.layout.user_read_listdetails);
        setContentView(R.layout.new_registration_verification);
        setContentView(R.layout.details);
        setContentView(R.layout.favorite_book);
        setContentView(R.layout.search_result);
        setContentView(R.layout.myread_list);
        setContentView(R.layout.user_info_change);
        setContentView(R.layout.withdrawal_verification);
    }

    //ヘッダー　メニュー表示
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar, menu);
        return true;
    }
}
