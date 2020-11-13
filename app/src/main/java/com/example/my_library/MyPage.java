package com.example.my_library;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


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

import java.util.Map;
import java.util.Objects;

public class MyPage extends AppCompatActivity {

    private static final String TAG = "MyPage";

    private static final String name = "user_name";
    private static final String image = "user_image";

    private TextView textViewDate;
    private TextView follo;
    private TextView followers;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRrf = db.collection("user").document("1801168@s.asojuku.ac.jp");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        textViewDate = findViewById(R.id.text_view_date);
        follo = findViewById(R.id.follo);
        followers = findViewById(R.id.followers);

        noteRrf.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String user_name = documentSnapshot.getString(name);
                            String user_image = documentSnapshot.getString(image);

                            //Map<String, Object> note = documentSnapshot.getData();

                            textViewDate.setText("name:" + user_name + "\n" + "img" + user_image);
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
                                .collection("").whereEqualTo("user_mail", true);

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
