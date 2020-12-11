package com.example.my_library;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
//参考URL
//https://akira-watson.com/android/asynctask.html
/**
 * 非同期処理を行うクラス.
 */
public final class AsyncHttpRequest extends AsyncTask<URL, Void, String> {
    private Activity mainActivity;

    private static final String TAG = "AsyncHttpRequest";
    private static final String bookAuthor_name = "author_name";
    private static final String bookCover = "cover";
    private static final String bookPrice = "price";
    private static final String bookGenre = "genre";
    private static final String bookTitle = "title";

    public AsyncHttpRequest(Activity activity) {
        // 呼び出し元のアクティビティ
        this.mainActivity = activity;
    }

    /**
     * 非同期処理で天気情報を取得する.
     * @param urls 接続先のURL
     * @return 取得した天気情報
     */
    @Override
    protected String doInBackground(URL... urls) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        final URL url = urls[0];
        HttpURLConnection con = null;
        final String title;
        final String author_name;
        final String genre;
        final String price;
        final String cover;
        final String ISBN;
        final String bookgenre;

        try {
            //指定のURLにGETで接続する設定
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            // リダイレクトを自動で許可しない設定
            con.setInstanceFollowRedirects(false);
            //WebAPIに接続する
            con.connect();

            final int statusCode = con.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.err.println("正常に接続できていません。statusCode:" + statusCode);
                return null;
            }

            // レスポンス(JSON文字列)を読み込む準備
            final InputStream in = con.getInputStream();
            String encoding = con.getContentEncoding();
            if(null == encoding){
                encoding = "UTF-8";//文字コード設定
            }
            final InputStreamReader inReader = new InputStreamReader(in, encoding);
            final BufferedReader bufReader = new BufferedReader(inReader);
            StringBuilder response = new StringBuilder();
            String line = null;
            // 1行ずつ読み込む
            while((line = bufReader.readLine()) != null) {
                response.append(line);
            }
            bufReader.close();
            inReader.close();
            in.close();

            // 受け取ったJSON文字列をパース
            Log.d(TAG,response.toString());

            JSONArray jsonArray = new JSONArray(response.toString());
            Log.d(TAG,"あああああああああああああああああなんでだよおおおおおおおおおおおおおおおおおおおおお");


                JSONObject jsonObject = jsonArray.getJSONObject(0);
                JSONObject priceArray = jsonObject.getJSONObject("onix").getJSONObject("ProductSupply").getJSONObject("SupplyDetail").getJSONArray("Price").getJSONObject(0);
                JSONObject genreArray = jsonObject.getJSONObject("onix").getJSONObject("DescriptiveDetail").getJSONArray("Subject").getJSONObject(0);

                ISBN = jsonObject.getJSONObject("summary").getString("isbn");
            title = jsonObject.getJSONObject("summary").getString("title");
            cover = jsonObject.getJSONObject("summary").getString("cover");
            author_name = jsonObject.getJSONObject("summary").getString("author");
            price  = priceArray.getString("PriceAmount");
            genre = genreArray.getString("SubjectCode");



           // title = getbook.getJSONObject("DescriptiveDetail").getJSONObject("TitleDetail").getJSONObject("TitleElement").getJSONObject("TitleText").getString("content");
           // Log.d(TAG,"あああああああああああああああああなんでだよおおおおおおおおおおおおおおおおおおおおお"+title);

            //author_name = getbook.getJSONObject("DescriptiveDetail").getJSONObject("Contributor").getJSONObject("PersonName").getString("content");
            //Log.d(TAG,"あああああああああああああああああなんでだよおおおおおおおおおおおおおおおおおおおおお"+author_name);

           // cover = getbook.getJSONObject("SupportingResource").getJSONObject("ResourceVersion").getString("ResourceLink");
           // Log.d(TAG,"あああああああああああああああああなんでだよおおおおおおおおおおおおおおおおおおおおお"+cover);

           // ISBN = getbook.getString("RecordReference");
           // Log.d(TAG,"あああああああああああああああああなんでだよおおおおおおおおおおおおおおおおおおおおお"+ISBN);

           // price = getbook.getJSONObject("ProductSupply").getJSONObject("SupplyDetail").getJSONObject("Price").getString("PriceAmount");
           // Log.d(TAG,"あああああああああああああああああなんでだよおおおおおおおおおおおおおおおおおおおおお"+price);

           // genre = getbook.getJSONObject("DescriptiveDetail").getJSONObject("Subject").getString("SubjectCode");
          //  Log.d(TAG,"あああああああああああああああああなんでだよおおおおおおおおおおおおおおおおおおおおお"+genre);


            int aaa = Integer.parseInt(genre);
            int bbb = aaa % 100;
            Integer oi = new Integer(bbb);

            bookgenre = oi.toString();




            Map<String, Object> book = new HashMap<>();
            book.put(bookAuthor_name,author_name);
            book.put(bookTitle,title);
            book.put(bookCover,cover);
            book.put(bookPrice,price);
            book.put(bookGenre,bookgenre);



            db.collection("book").document(ISBN)
                    .set(book)
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



            String mas ="";

            return mas = "登録できたよ";


        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
    }

    /**
     * 非同期処理が終わった後の処理.
     * @param result 非同期処理の結果得られる文字列
     */
    @Override
    protected void onPostExecute(String result) {
        try {
            TextView tv = mainActivity.findViewById(R.id.mas);
            tv.setText(result);

        }catch (Exception ex){

        }
    }
}

