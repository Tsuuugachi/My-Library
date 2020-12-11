package com.example.my_library.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.my_library.R;

import java.util.ArrayList;
import java.util.HashMap;

public class BookListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private int mLayoutID;
    ArrayList<HashMap<String, Object>> bookList;

    static class ViewHolder {
        ImageView book_image;
        TextView book_name;
        TextView book_author;
        TextView message;
        TextView datetime;
    }

    public BookListAdapter(Context context, ArrayList<HashMap<String, Object>> dataList, int rowLayout) {
        this.mInflater = LayoutInflater.from(context);
        this.mLayoutID = rowLayout;
        this.bookList = dataList;
    }

    @Override
    public int getCount() {
        return this.bookList.size();
    }

    @Override
    public HashMap<String, Object> getItem(int position) {
        return this.bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(mLayoutID, null);
            holder = new ViewHolder();
            holder.book_image = convertView.findViewById(R.id.book_image);
            holder.book_name = convertView.findViewById(R.id.book_name);
            holder.book_author = convertView.findViewById(R.id.book_author);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, Object> nextData = getItem(position);

        holder.message.setText((String) nextData.get("message"));
        holder.datetime.setText((String) nextData.get("date"));
        holder.book_image.setImageBitmap((Bitmap) nextData.get("book_image"));

        return convertView;
    }
}