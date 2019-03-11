package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class bookAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<book> myBook;
    public bookAdapter(Context context, ArrayList<book> data) {
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
            convertView = mInflater.inflate(R.layout.my_book_list_item, parent, false);
            holder = new ViewHolder();

            holder.image = (ImageView) convertView.findViewById(R.id.BookImage);
            holder.bookName = (TextView) convertView.findViewById(R.id.BookName);
            //holder.info = (ImageButton) convertView.findViewById(R.id.infoButton);
            holder.currentBorrower = (TextView) convertView.findViewById(R.id.CurrentBorrower);
            holder.description = (TextView) convertView.findViewById(R.id.descrip);
            holder.status = (TextView) convertView.findViewById(R.id.Stat);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        book book = myBook.get(position);
        holder.bookName.setText(book.getName());
        holder.status.setText(book.getStatus());
        //holder.image.setImageDrawable();
        holder.currentBorrower.setText(book.getBorrowerName());
        holder.description.setText(book.getDescription());
        //holder.info.set
        return convertView;

    }

    private class ViewHolder{
        ImageView image;
        TextView bookName;
        TextView status;
        TextView currentBorrower;
        TextView description;
        //ImageButton info;
    }


}