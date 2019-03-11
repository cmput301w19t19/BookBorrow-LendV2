package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchPersonAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<NormalUser> myUser;
    public SearchPersonAdapter(Context context, ArrayList<NormalUser> data) {
        mInflater = LayoutInflater.from(context);
        myUser = data;
    }

    // get the length of the data
    @Override
    public int getCount(){
        return myUser.size();
    }

    @Override
    public Object getItem(int position){
        return myUser.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        SearchPersonAdapter.ViewHolder holder = null;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_layout_for_person, parent, false);
            holder = new SearchPersonAdapter.ViewHolder();

            //holder.image = (ImageButton) convertView.findViewById(R.id.userImage);
            holder.userName = (TextView) convertView.findViewById(R.id.UserName);
            holder.userEmail = (TextView) convertView.findViewById(R.id.Email);

            convertView.setTag(holder);
        }
        else{
            holder = (SearchPersonAdapter.ViewHolder) convertView.getTag();
        }

        NormalUser NormalUsers = myUser.get(position);
        holder.userName.setText(NormalUsers.getName());
        //holder.image.setImageDrawable();
        holder.userEmail.setText(NormalUsers.getEmail());
        return convertView;

    }

    private class ViewHolder{
        //ImageButton image;
        TextView userName;
        TextView userEmail;
    }



}
