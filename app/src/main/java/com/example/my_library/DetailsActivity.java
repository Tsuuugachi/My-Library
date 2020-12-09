package com.example.my_library;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class DetailsActivity extends AppCompatActivity {

    private final FirebaseFirestore db;
    DetailsActivity(FirebaseFirestore db){
        this.db = db;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        loadImage();
    }


        public void loadImage(){

           FirebaseFirestore storage = FirebaseFirestore.getInstance();

           //storageを参照
           StorageReference = storage.getReference();
           StorageReference pathReference = storageRef.child("Cover/51e5ReBPtSL._SX344_BO1,204,203,200_.jpg");

           //メモリにダウンロード
           StorageReference imageRef = storageRef.child("Cover/51e5ReBPtSL._SX344_BO1,204,203,200_.jpg");

           final long ONE_MEGABYTE = 1024 * 1024;
           imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
               @Override
               public void onSuccess(byte[] bytes) {
                   //画像データ処理
                   findViewById(R.id.Book_imageView);
               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                  //エラー処理
               }
           });
         }


        public void loadText(){

            CollectionReference image = db.collection("read_list");

            Map<String, Object> data1 = new HashMap<>();
            data1.put("name", "San Francisco");
            data1.put("state", "CA");
            data1.put("country", "USA");
            data1.put("capital", false);
            data1.put("population", 860000);
            data1.put("regions", Arrays.asList("west_coast", "norcal"));
            image.document("SF").set(data1);
        }


}
