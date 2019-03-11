package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchBookAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<book> myBook;
    public SearchBookAdapter(Context context, ArrayList<book> data) {
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
            convertView = mInflater.inflate(R.layout.list_layout, parent, false);
            holder = new SearchBookAdapter.ViewHolder();

            holder.image = (ImageView) convertView.findViewById(R.id.book_image);
            holder.bookName = (TextView) convertView.findViewById(R.id.name_text);
            holder.description = (TextView) convertView.findViewById(R.id.description_text);
            holder.status = (TextView) convertView.findViewById(R.id.status_text);
            holder.owner = (TextView) convertView.findViewById(R.id.owner_text);

            convertView.setTag(holder);
        }
        else{
            holder = (SearchBookAdapter.ViewHolder) convertView.getTag();
        }

        book book = myBook.get(position);
        holder.bookName.setText(book.getName());
        holder.status.setText(book.getStatus());
        holder.owner.setText(book.getOwnerName());
        //holder.image.setImageDrawable();
        holder.description.setText(book.getDescription());
        return convertView;

    }

    private class ViewHolder{
        ImageView image;
        TextView bookName;
        TextView status;
        TextView description;
        TextView owner;
    }



}
