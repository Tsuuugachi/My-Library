package com.example.my_library;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        setContentView(R.layout.new_registration);
        setContentView(R.layout.login);
        setContentView(R.layout.user_followlist);
        setContentView(R.layout.user_follower);
        setContentView(R.layout.read_list_ranking);
        setContentView(R.layout.user_follow);
        setContentView(R.layout.user_read_listdetails);
        setContentView(R.layout.new_registration_verification);
        setContentView(R.layout.search_result);
    }

}