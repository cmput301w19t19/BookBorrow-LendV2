package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class BorrowingBookAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<book> myBook;
    public BorrowingBookAdapter(Context context, ArrayList<book> data) {
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
        ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.borrow_book_item, parent, false);
            holder = new BorrowingBookAdapter.ViewHolder();

            holder.image = (ImageView) convertView.findViewById(R.id.BorBookImage);
            holder.bookName = (TextView) convertView.findViewById(R.id.BorBookName);
            holder.OwnerName = (TextView) convertView.findViewById(R.id.OwnerName);
            holder.description = (TextView) convertView.findViewById(R.id.BorBookDescrip);
            holder.rating = (TextView) convertView.findViewById(R.id.BorRating_on);

            convertView.setTag(holder);
        }
        else{
            holder = (BorrowingBookAdapter.ViewHolder) convertView.getTag();
        }

        book book = myBook.get(position);
        holder.bookName.setText(book.getName());
        holder.OwnerName.setText(book.getOwnerName());
        //holder.image.setImageDrawable();
        holder.rating.setText(book.getBookRating().toString());
        holder.description.setText(book.getDescription());
        //holder.info.set
        return convertView;

    }

    private class ViewHolder{
        ImageView image;
        TextView bookName;
        TextView OwnerName;
        TextView description;
        TextView rating;
    }


}