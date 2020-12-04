package com.example.my_library;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ScrollView;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_followlist_frame);
    }



    //ヘッダー　メニュー表示
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar, menu);

        return true;
    }




}
