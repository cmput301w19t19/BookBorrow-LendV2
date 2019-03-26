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

package com.example.y.BookEX;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The type Search Book adapter.
 *
 * @version 1.0
 * @author: Bowei Li
 * @see: BaseAdapter
 */
public class SearchBookAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<Book> myBook;

    /**
     * Instantiates a new Search Book adapter.
     *
     * @param context the context
     * @param data    the data
     */
    public SearchBookAdapter(Context context, ArrayList<Book> data) {
        mInflater = LayoutInflater.from(context);
        myBook = data;
    }

    /**
     * get the size of the arraylist myBook
     * @return myBook.size()
     */
    // get the length of the data
    @Override
    public int getCount(){
        return myBook.size();
    }

    /**
     * return the Book at the given position in myBook.
     * @param position
     * @return myBook.get(position)
     */
    @Override
    public Object getItem(int position){
        return myBook.get(position);
    }

    /**
     * return the given position
     * @param position
     * @return position
     */
    @Override
    public long getItemId(int position){
        return position;
    }

    /**
     * get the View of SearchBookAdapter
     * @param position
     * @param convertView
     * @param parent
     * @return convertView
     */
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

        Book book = myBook.get(position);
        holder.bookName.setText(book.getName());
        holder.status.setText(book.getStatus());
        holder.owner.setText(book.getOwnerID());
        holder.image.setImageBitmap(book.getImage());
        holder.description.setText(book.getDescription());
        return convertView;

    }

    /**
     * a viewHolder object used for SearchBookAdapter
     */
    private class ViewHolder{

        ImageView image;

        TextView bookName;

        TextView status;

        TextView description;

        TextView owner;
    }



}
