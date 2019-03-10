package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;

import com.example.y.bookborrow_lendv2.R;
import com.example.y.bookborrow_lendv2.book;
import com.example.y.bookborrow_lendv2.bookAdapter;

import java.util.ArrayList;

public class BorrowerRequestAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private ArrayList<book> myBook;


    public BorrowerRequestAdapter(Context context, ArrayList<book> data){

        mInflater = LayoutInflater.from(context);
        myBook = data;
    }

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

    //get a view holder
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ViewHolder holder = null;

        if(convertView == null){
            //also  use my_book_list_item layout
            convertView = mInflater.inflate(R.layout.my_book_list_item, parent, false);
            holder = new ViewHolder();


            //convert to the my_book_list_item layout, ids are in there
            holder.image = (ImageView) convertView.findViewById(R.id.BookImage);
            holder.bookName = (TextView) convertView.findViewById(R.id.BookName);
            holder.currentBorrower = (TextView) convertView.findViewById(R.id.CurrentBorrower);
            holder.description = (TextView) convertView.findViewById(R.id.descrip);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        book book = myBook.get(position);
        holder.bookName.setText(book.getName());
        holder.currentBorrower.setText(book.getBorrowerName());
        holder.description.setText(book.getDescription());

        return convertView;
    }

    //this view holder only for this adapter,items are different
    private class ViewHolder{

        TextView bookName;
        ImageView image;
        TextView currentBorrower;
        TextView description;
    }

}
