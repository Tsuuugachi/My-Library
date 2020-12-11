package com.example.my_library.async;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
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
     * 非同期処理でopendbの情報を取得する.
     * @param urls 接続先のURL
     * @return 取得したメッセージ
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
        final String mas;

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
                return mas = "正しいISBNコードを打ち込んでください";
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

            JSONObject jsonObject = jsonArray.getJSONObject(0);
            JSONObject priceArray = jsonObject.getJSONObject("onix").getJSONObject("ProductSupply").getJSONObject("SupplyDetail").getJSONArray("Price").getJSONObject(0);
            JSONObject genreArray = jsonObject.getJSONObject("onix").getJSONObject("DescriptiveDetail").getJSONArray("Subject").getJSONObject(0);
            ISBN = jsonObject.getJSONObject("summary").getString("isbn");
            title = jsonObject.getJSONObject("summary").getString("title");
            cover = jsonObject.getJSONObject("summary").getString("cover");
            author_name = jsonObject.getJSONObject("summary").getString("author");
            price  = priceArray.getString("PriceAmount");
            genre = genreArray.getString("SubjectCode");
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
            return mas = "登録完了しました";
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