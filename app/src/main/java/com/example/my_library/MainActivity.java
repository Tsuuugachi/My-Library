package com.example.my_library;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.View;
import android.widget.EditText;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
    }


    //ヘッダー　メニュー表示
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar, menu);

        return true;
    }

    //アプリバー　クリックイベント
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.readlist_Button:
                setContentView(R.layout.myread_list_details);
                return true;
            case R.id.ranking_Button:
                setContentView(R.layout.read_list_ranking);
                return true;
            case R.id.books_Button:
                setContentView(R.layout.favorite_book);
                return true;
            case R.id.favorite_Readrist:
                setContentView(R.layout.favorite_myread_list);
                return true;
            case R.id.bookshelf_Button:
                setContentView(R.layout.bookshelf);
                return true;
            case R.id.barcode_Button:
                setContentView(R.layout.barcode_reading);
                return true;
            case R.id.user_Button:
                setContentView(R.layout.mypage);
                return true;
            case R.id.userChange_button:
                setContentView(R.layout.user_info_change);
                return true;
            case R.id.logout_Button:
                setContentView(R.layout.logout_verification);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
