package com.example.my_library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class MyPage extends AppCompatActivity {


    private static final String TAG = "MyPage";

    private static final String name = "user_name";
    private static final String image = "user_image";

    private TextView nameDate;
    private TextView follo;
    private TextView followers;
    private ImageView imageDate;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRrf = db.collection("user").document("1801168@s.asojuku.ac.jp");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        nameDate = findViewById(R.id.Name_View);
        follo = findViewById(R.id.Follow_View);
        followers = findViewById(R.id.Follower_View);

        noteRrf.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user_name = documentSnapshot.getString(name);

                            //Map<String, Object> note = documentSnapshot.getData();

                            nameDate.setText(user_name);

                            // Reference to an image file in Cloud Storage
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference();

                            // ImageView in your Activity
                            ImageView imageView = findViewById(R.id.icon_View);

                            // Download directly from StorageReference using Glide
                            // (See MyAppGlideModule for Loader registration)
                            Glide.with(this )
                                    .load(storageReference)
                                    .into(imageView);

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

        Query capitalCities = db.collection("user").document("1801168@s.asojuku.ac.jp")
                                .collection("follow").whereEqualTo("user_mail", true);

        capitalCities
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }







}
