package com.example.my_library;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class UserDeleteActivity extends AppCompatActivity {

    private static final String TAG = "UserDeleteActivity";

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    DataBean databean =new DataBean();
    String email =databean.getMail();

 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.withdrawal_verification);


     mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.Withdrawal).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    db.collection("user").document(email).collection("follow")
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    db.collection("user").document(email)
                                            .collection("follow").document(document.getId()).delete();
                                }
                            }

                        }
                            });

                db.collection("user").document(email).collection("follower")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        db.collection("user").document(email)
                                                .collection("follower").document(document.getId()).delete();
                                    }
                                }
                            }
                        });

                db.collection("user").document(email).collection("login_status")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        db.collection("user").document(email)
                                                .collection("login_status").document(document.getId()).delete();
                                    }
                                }
                            }
                        });
               db.collection("user").document(email).collection("read_list")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                         final String docId_r = document.getId();

                                        db.collection("user").document(email).collection("read_list")
                                                .document(docId_r).collection("read_list_comment").get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                                db.collection("user").document(email)
                                                                        .collection("read_list").document(docId_r)
                                                                        .collection("read_list_comment").document(document.getId()).delete();
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                }
                            }
                        });


                db.collection("user").document(email).collection("read_list")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        db.collection("user").document(email)
                                                .collection("read_list").document(document.getId()).delete();
                                    }
                                }
                            }
                        });
                               db.collection("user").document(email).delete();

                                Log.d(TAG, "Document successfully deleted!");
                        }
                    });
                }
            }
