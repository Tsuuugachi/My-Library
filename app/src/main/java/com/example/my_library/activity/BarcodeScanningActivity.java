package com.example.my_library.activity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_library.R;

import java.net.MalformedURLException;
import java.net.URL;

public class BarcodeScanningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barcode_reading);
    }
    public void inbook(View v) {
        EditText etxtIsbn = (EditText)findViewById(R.id.editTextTextPersonName2);
        String Isbn = etxtIsbn.getText().toString();
        // 非同期処理(AsyncHttpRequest#doInBackground())を呼び出す
        try {
            new com.example.my_library.AsyncHttpRequest(this)
                    .execute(new URL("https://api.openbd.jp/v1/get?isbn="+Isbn));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}