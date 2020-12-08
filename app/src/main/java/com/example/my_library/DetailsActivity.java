package com.example.my_library;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;

public class DetailsActivity extends AppCompatActivity {

    CollectionReference cities = db.collection("cities");
}
