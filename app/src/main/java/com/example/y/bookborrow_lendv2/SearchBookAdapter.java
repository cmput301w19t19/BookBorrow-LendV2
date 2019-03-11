package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchBookAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Book> myBook;
    public SearchBookAdapter(Context context, ArrayList<Book> data) {
        mInflater = LayoutInflater.from(context);
        myBook = data;
    }

    // get the length of the data
    @Override
    public int getCount(){
        return myBook.size();
    }

    @Override
    public Object getItem(int position){
        return myBook.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        SearchBookAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.activity_search_result_for_book, parent, false);
            holder = new SearchBookAdapter.ViewHolder();

            holder.image = (ImageButton) convertView.findViewById(R.id.BookImage);
            holder.bookName = (TextView) convertView.findViewById(R.id.BookName);
            holder.description = (TextView) convertView.findViewById(R.id.descrip);
            holder.status = (TextView) convertView.findViewById(R.id.Stat);

            convertView.setTag(holder);
        }
        else{
            holder = (SearchBookAdapter.ViewHolder) convertView.getTag();
        }

        Book book = myBook.get(position);
        holder.bookName.setText(book.getName());
        holder.status.setText(book.getStatus());
        //holder.image.setImageDrawable();
        holder.description.setText(book.getDescription());
        return convertView;

    }

    private class ViewHolder{
        ImageButton image;
        TextView bookName;
        TextView status;
        TextView description;
    }



}
