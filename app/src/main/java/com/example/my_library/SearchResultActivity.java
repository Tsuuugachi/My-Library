package com.example.my_library;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.my_library.bean.DataBean;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    private static final String TAG = "SearchResultActivity";


    ListView lv;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String imageGet;
    private String titleGet;

    DataBean databean =new DataBean();
    String email =databean.getMail();



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);


        Intent intent = getIntent();
        final String input = intent.getStringExtra(SearchActivity.input_data);



        lv = (ListView) findViewById(R.id.listview);

        db.collection("book").whereEqualTo("title", input).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                imageGet = document.getString("cover");
                                titleGet = document.getString("title");

                                List<ListItem> list = new ArrayList<ListItem>();
                                Log.d(TAG, titleGet);
                                Log.d(TAG, imageGet);

                                ListItem item = new ListItem();
                                item.setText("タイトル　　" + titleGet);
                                item.setImageId(imageGet);
                                list.add(item);

                                ImageArrayAdapter adapter =
                                        new ImageArrayAdapter(SearchResultActivity.this, R.layout.list_item, list);

                                lv = (ListView) findViewById(R.id.listview);
                                lv.setAdapter(adapter);

                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }


                    }

                });
       /* findViewById(R.id.item_text).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                db.collection("user").whereEqualTo("title", input).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title = document.getId();
                                Intent intent = new Intent(getApplication(), UserAuthActivity.class);
                                startActivity(intent);
                            }
                        }

                    }

                });
            }
        });

        */
    }

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

    public class ListItem {

        private String imageId;
        private String text;

        public String getImageId() {
            return imageId;
        }
        public void setImageId(String imageId) {
            this.imageId = imageId;
        }
        public String getText() {
            return text;
        }
        public void setText(String text) {
            this.text = text;
        }

    }

    public class ImageArrayAdapter extends ArrayAdapter<ListItem> {

        private int resourceId;
        private List<ListItem> items;
        private LayoutInflater inflater;

        public ImageArrayAdapter(Context context, int resourceId, List<ListItem> items) {
            super(context, resourceId, items);

            this.resourceId = resourceId;
            this.items = items;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView != null) {
                view = convertView;
            } else {
                view = this.inflater.inflate(this.resourceId, null);
            }

            ListItem item = this.items.get(position);

            // テキストをセット
            TextView appInfoText = (TextView)view.findViewById(R.id.item_text);
            appInfoText.setText(item.getText());

            // アイコンをセット
            ImageView appInfoImage = (ImageView)view.findViewById(R.id.item_image);

            SearchResultActivity.ImageGetTask task2 = new SearchResultActivity.ImageGetTask(appInfoImage);
            task2.execute(item.imageId);

            return view;
        }
    }

}
