package com.example.my_library;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Details extends AppCompatActivity {

    private ListView lv;

    private static final String TAG = "Details";
    private static final String Cover = "cover";
    private static final String Title = "title";
    private static final String Author_name = "author_name";
    private static final String Price = "price";
    private static final String img = "user_image";
    private static final String CNT = "review_content";
    private static final String Mail = "user_mail";
    private static final String name = "user_name";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ImageView cover;
    private TextView title;
    private TextView author_name;
    private TextView price;
    private String url;
    private  ImageView icon;
    private DataBean data;
    private List<ListBook> list;
    private ImageView image;
    private TextView u_content;
    private TextView u_name;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        //Intent rIntent = getIntent();
        //final String ISBN = rIntent.getStringExtra("ISBN");
        final String ISBN = "9784822271992";


//ログインユーザーのアイコン取得
        db.collection("user").document("1801168@s.asojuku.ac.jp").get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            url = documentSnapshot.getString(img);
                            //Map<String, Object> note = documentSnapshot.getData();
//imageを取得
                           // icon = (ImageView)Details.this.findViewById(R.id.icon_imageView);
//画像取得スレッド起動
                           // ImageGetTask task = new ImageGetTask(icon);
                           // task.execute(url);
                        } else {
                            Toast.makeText(Details.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Details.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

        //本の情報を取得・表示
        db.collection("book").document(ISBN)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String get_cover = documentSnapshot.getString(Cover);
                            String get_title = documentSnapshot.getString(Title);
                            String get_author_name = documentSnapshot.getString(Author_name);
                            String get_price = documentSnapshot.getString(Price);
                            //Map<String, Object> note = documentSnapshot.getData();

                            title.setText(get_title);
                            author_name.setText(get_author_name);
                            price.setText(get_price);

                            cover = (ImageView)Details.this.findViewById(R.id.Book_imageView);
//画像取得スレッド起動
                           ImageGetTask task = new ImageGetTask(cover);
                           task.execute(get_cover);


                        } else {
                            Toast.makeText(Details.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Details.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });

        //コメント表示
        db.collection("book").document(ISBN).collection("review")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                final String num = document.getId();

                                db.collection("book").document(ISBN).collection("review").document(num)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()){
                                                    String get_user_mail = documentSnapshot.getString(Mail);
                                                    final String get_review_content = documentSnapshot.getString(CNT);
                                                    //Map<String, Object> note = documentSnapshot.getData();

                                                    db.collection("user").document(Mail)
                                                            .get()
                                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                    if (documentSnapshot.exists()){
                                                                        String get_img = documentSnapshot.getString(img);
                                                                        String get_name = documentSnapshot.getString(name);

                                                                        ListBook item = new ListBook();
                                                                        item.setName(get_name);
                                                                        item.setImage(get_img);
                                                                        item.setContent(get_review_content);

                                                                        list.add(item);


                                                                        BookArrayAdapter adapter = new BookArrayAdapter(Details.this, R.layout.datails_review, list);
                                                                        lv = (ListView) findViewById(R.id.comment);
                                                                        lv.setAdapter(adapter);

                                                                    } else {
                                                                        Toast.makeText(Details.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            })
                                                            .addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    Toast.makeText(Details.this, "Error!", Toast.LENGTH_SHORT).show();
                                                                    Log.d(TAG, e.toString());
                                                                }
                                                            });
                                                } else {
                                                    Toast.makeText(Details.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Details.this, "Error!", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, e.toString());
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        title = findViewById(R.id.Title_View);
        author_name = findViewById(R.id.authorV);
        price = findViewById(R.id.priceV);
    }

    public class ListBook {
        private String image;
        private String name;
        private String content;

        public String getImage() {
            return image;
        }
        public void setImage(String imageId) {
            this.image = imageId;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public class BookArrayAdapter extends ArrayAdapter<ListBook> {
        private int resourceId;
        private List<ListBook> items;
        private LayoutInflater inflater;
        public BookArrayAdapter(Context context, int resourceId, List<ListBook> items) {
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
                view = inflater.inflate(this.resourceId, null);
            }

            final ListBook item = items.get(position);

            TextView appInfoTitle = (TextView)view.findViewById(R.id.name);
            appInfoTitle.setText(item.getName());
            // 著者名をセット
            TextView appInfoName = (TextView)view.findViewById(R.id.comment);
            appInfoName.setText(item.getContent());
            // アイコンをセット
            image = (ImageView) view.findViewById(R.id.icon_imageView);
            ImageGetTask task2 = new ImageGetTask(image);
            task2.execute(item.getImage());
            return view;
        }
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
            // 取得した画像をImageButtonに設定します。
            image.setImageBitmap(result);
        }
    }

    public void add(View view){
        //Intent rIntent = getIntent();
       // String ISBN = rIntent.getStringExtra("ISBN");
        String ISBN = "9784822271992";
        EditText Review_edit_text = (EditText)findViewById(R.id.Review_edit_text);
        //data = (DataBean) getApplication();
        //String gettxt = data.getMail();
        Map<String, Object> add = new HashMap<>();
        add.put("review_content", Review_edit_text );
        add.put("user_mail", "1801168@asojuku.ac.jp");
        //add.put("user_mail", gettxt);

        db.collection("book").document(ISBN).collection("review").document("1801168@s.asojuku.ac.jp")
                .set(add)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });




        Intent intent = new Intent(Details.this, Details.class);
        startActivity(intent);

    }


}
