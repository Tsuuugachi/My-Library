package com.example.my_library;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    /**
     * アクティビティ生成時に呼ばれる
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.user_followlist_frame);

            // レイアウトからリストビューを取得
            ListView listView = (ListView)findViewById(R.id.account);

            // リストビューに表示する要素を設定
            ArrayList<SampleListItem> listItems = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.id.account_ImageView);
                SampleListItem item = new SampleListItem(bmp, "name" + String.valueOf(i));
                listItems.add(item);
            }

            // 出力結果をリストビューに表示
            SampleListAdapter adapter = new SampleListAdapter(this, R.layout.user_followlist, listItems);
            listView.setAdapter(adapter);

    }



    //ヘッダー　メニュー表示
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menubar, menu);

        return true;
    }




}
