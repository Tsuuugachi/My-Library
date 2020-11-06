package com.example.my_library;

import android.app.Activity;

public class MenuActivity extends Activity {

    //SearchView追加
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

}
