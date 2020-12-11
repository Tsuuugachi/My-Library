package com.example.my_library.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

import com.example.my_library.R;

public class UserLoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    }
    @Override
    protected void onResume() {
        super.onResume();

        Button login_auth = (Button)findViewById(R.id.Login_button);
        Button create_user = (Button)findViewById(R.id.Sign_up_button);

        login_auth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //マイページ画面へ遷移
                Intent intent = new Intent(UserLoginActivity.this, MyPageActivity.class);
                startActivity(intent);
            }
        });

        create_user.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //新規登録画面へ遷移
                Intent intent = new Intent(UserLoginActivity.this,UserCreateActivity.class);
                startActivity(intent);
            }
        });
    }


    //ヘッダー　メニュー表示
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar, menu);

        return true;
    }
}