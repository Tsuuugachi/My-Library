package com.example.my_library;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.my_library.bean.DataBean;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
public class MyPage extends AppCompatActivity {
    private DataBean data;
    private static final String TAG = "MyPage";
    private static final String name = "user_name";
    private static final String img = "user_image";
    private String url;
    Bitmap bitmap = null;
    private TextView nameDate;
    private  ImageView icon;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        getname();
        includesForCreateReference();
    }
    public void getname(){
        data = (DataBean) getApplication();
        db.collection("user").document(data.getMail()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String usar_name = documentSnapshot.getString(name);
                            //Map<String, Object> note = documentSnapshot.getData();
                            nameDate.setText(usar_name);
                        } else {
                            Toast.makeText(MyPage.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MyPage.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
        nameDate = findViewById(R.id.Name_View);
    }
    public void includesForCreateReference(){
        data = (DataBean) getApplication();
        db.collection("user").document(data.getMail()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            url = documentSnapshot.getString(img);
                            //Map<String, Object> note = documentSnapshot.getData();
//imageを取得
                            icon = (ImageView)MyPage.this.findViewById(R.id.icon_View);
//画像取得スレッド起動
                            ImageGetTask task = new ImageGetTask(icon);
                            task.execute(url);
                        } else {
                            Toast.makeText(MyPage.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MyPage.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }
    // Image取得用スレッドクラス
    class ImageGetTask extends AsyncTask<String,Void,Bitmap> {
        private ImageView image;
        public ImageGetTask(ImageView _image) {
            image = _image;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap image;
            try {
                URL imageUrl = new URL(params[0]);
                InputStream imageIs;
                imageIs = imageUrl.openStream();
                image = BitmapFactory.decodeStream(imageIs);
                return image;
            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            // 取得した画像をImageViewに設定します。
            image.setImageBitmap(result);
        }
    }
}