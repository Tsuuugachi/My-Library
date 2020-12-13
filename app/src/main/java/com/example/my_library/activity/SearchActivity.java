package com.example.my_library.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.my_library.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Context mContext;

    public static final String input_data
            = "com.example.my_library.INPUT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input);

        findViewById(R.id.search_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input = ((TextView) findViewById(R.id.input_txt)).getText().toString();
                Intent intent = new Intent(getApplication(), SearchResultActivity.class);
                intent.putExtra(input_data, input);
                startActivity(intent);

            }

        });



    }
}


            /*    CollectionReference citiesRef = db.collection("book");
                Query query = citiesRef.whereEqualTo("title", input);

                db.collection("book").whereEqualTo("title", input).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                                                       @Override
                                                       public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                       List<String> titles = new ArrayList<>();
                                                       if (task.isSuccessful()) {
                                                           for (QueryDocumentSnapshot document : task.getResult()) {
                                                               Log.d(TAG, document.getId() + " => " + document.getData());

                                                               String titledata = document.getId();
                                                               titles.add(titledata);
                                                           }
                                                       }
                                                       else {
                                                           Log.d(TAG, "Error getting documents: ", task.getException());
                                                       }

                                                       ListView listView = (ListView) findViewById(R.id.missionList);
                                                       ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, R.layout.list_item,R.id.mission_title, titles);
                                                       listView.setAdapter(arrayAdapter);
                                                   }

                                               });
              /*          .addSnapshotListener(new EventListener<QuerySnapshot>() {

                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.w(TAG, "Listen failed.", e);
                                    return;
                                }

                                for (QueryDocumentSnapshot doc : value) {
                                    if (doc.get("title") != null) {
                                        titles.add(doc.getString("title"));

                                    }
                                }



                            }

                        });


            }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.list, titles);
                ListView listView = new ListView(this);

               // ListView listView = (ListView) findViewById(R.id.list_item);
                listView.setAdapter(adapter);




        });

               */





