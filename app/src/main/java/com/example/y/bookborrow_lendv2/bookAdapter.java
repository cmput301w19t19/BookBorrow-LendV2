/*
 * Copyright 2019 TEAM19
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * book adapter used in MyBookList
 * @author Bowei Li
 * @version 1.0
 */
public class bookAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<book> myBook;
    public bookAdapter(Context context, ArrayList<book> data) {
        mInflater = LayoutInflater.from(context);
        myBook = data;
    }

    /**
     * get the length of myBook
     * @return myBook.size()
     */
    @Override
    public int getCount(){
        return myBook.size();
    }

    /**
     * get the item at the given position of myBook
     * @param position
     * @return myBook.get(position);
     */
    @Override
    public Object getItem(int position){
        return myBook.get(position);
    }

    /**
     * get the position
     * @param position
     * @return position
     */
    @Override
    public long getItemId(int position){
        return position;
    }

    /**
     * get the view of book adapter
     * @param position
     * @param convertView
     * @param parent
     * @return convertView
     */
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
        holder.currentBorrower.setText(book.getBorrowerID());
        holder.description.setText(book.getDescription());
        holder.image.setImageBitmap(book.getImage());
        //holder.info.set
        return convertView;

    }

    /**
     * a Viewholder class used for bookAdapter
     */
    private class ViewHolder{
        ImageView image;
        TextView bookName;
        TextView status;
        TextView currentBorrower;
        TextView description;
        //ImageButton info;
    }


}