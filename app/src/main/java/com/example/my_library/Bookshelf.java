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
import android.widget.Button;
import android.widget.ImageButton;
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
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Bookshelf extends AppCompatActivity {

    private ListView lv;
    private DataBean data;


    private static final String TAG = "Bookshelf";

    private static final String title = "title";
    private static final String author_name = "author_name";
    private static final String cover = "cover";

    private TextView nameDate;
    private ImageButton book_cover;
    private List<ListBook> list;
    ImageView image;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookshelf);

        FirebaseStorage storage = FirebaseStorage.getInstance();

        list = new ArrayList<ListBook>();
        getbook();

    }



    public void getbook(){
        data = (DataBean)getApplication();
        String mail = data.getMail();

        db.collection("user").document(mail).collection("history").
                get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                final String num = document.getId();

                                db.collection("book").document(num)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                if (documentSnapshot.exists()){
                                                    String book_title  = documentSnapshot.getString(title);
                                                    String book_author_name  = documentSnapshot.getString(author_name);
                                                    String url = documentSnapshot.getString(cover);
                                                    //Map<String, Object> note = documentSnapshot.getData();
                                                    ListBook item = new ListBook();
                                                    item.setName(book_author_name);
                                                    item.setTitle(book_title);
                                                    item.setImage(url);
                                                    item.setIsbn(num);

                                                    list.add(item);


                                                    BookArrayAdapter adapter = new BookArrayAdapter(Bookshelf.this, R.layout.bookshelf_book, list);
                                                    lv = (ListView) findViewById(R.id.book);
                                                    lv.setAdapter(adapter);
                                                } else {
                                                    Toast.makeText(Bookshelf.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Bookshelf.this, "Error!", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, e.toString());
                                            }
                                        });

                            }


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public class ListBook {
        private String image;
        private String name;
        private String title;
        private String isbn;
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
        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
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

        class ViewHolder {
            Button details_button;
            Button delete_button;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder vh = null;

            if (convertView != null) {
                view = convertView;
            } else {
                view = inflater.inflate(this.resourceId, null);
            }

            final ListBook item = items.get(position);

            if (view == null) {
                // カスタムビューをリストビューに登録する
                view = this.inflater.inflate(this.resourceId, parent, false);
                // ビューホルダーとしてビューにリストビューの項目を登録する
                vh = new ViewHolder();
                vh.delete_button = (Button)view.findViewById(R.id.delete);

                //////////////////////////////////////////////////////////////////
                //　削除ボタンが押されたときに削除できるように
                vh.delete_button.setTag(this.getItem(position));
                // 削除ボタンがクリックされたときの通知先を設定する
                vh.delete_button.setOnClickListener(new View.OnClickListener() {

                    // ボタンがクリックされました
                    public void onClick(View v) {
                        data = (DataBean)getApplication();
                        String mail = data.getMail();
                        db.collection("user").document(mail).collection("history").document(item.getIsbn())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error deleting document", e);
                                    }
                                });
                    }
                });
                vh.details_button = (Button)view.findViewById(R.id.details);

                //　詳細ボタンが押されたときに画面遷移できるように
                vh.details_button.setTag(this.getItem(position));
                // 詳細ボタンがクリックされたときの通知先を設定する
                vh.details_button.setOnClickListener(new View.OnClickListener() {

                    // ボタンがクリックされました
                    public void onClick(View v) {
                        Intent intent = new Intent(Bookshelf.this, Details.class);
                        intent.putExtra("msg",item.getIsbn());
                        startActivity(intent);
                    }
                });

                // ビューに登録する
                view.setTag(vh);
            } else {
                vh = (ViewHolder)view.getTag();
            }



            TextView appInfoTitle = (TextView)view.findViewById(R.id.signature);
            appInfoTitle.setText(item.getTitle());
            // 著者名をセット
            TextView appInfoName = (TextView)view.findViewById(R.id.author_name);
            appInfoName.setText(item.getName());
            // アイコンをセット
            image = (ImageView) view.findViewById(R.id.imageBook);
            Bookshelf.ImageGetTask task2 = new Bookshelf.ImageGetTask(image);
            task2.execute(item.getImage());
            return view;
        }
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
            // 取得した画像をImageButtonに設定します。
            image.setImageBitmap(result);
        }
    }
}



