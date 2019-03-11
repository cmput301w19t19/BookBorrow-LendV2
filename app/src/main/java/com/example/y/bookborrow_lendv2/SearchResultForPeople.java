package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;

public class SearchResultForPeople extends AppCompatActivity {

    private ListView mResultList;
    private DatabaseReference mUserDatabase;
    private ArrayList<NormalUser> users = new ArrayList<>();
    private SearchPersonAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_for_people);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("users");
        //mSearchWord = findViewById(R.id.editText);
        Button newSearch = (Button) findViewById(R.id.NewSearch);
        Button backHome = (Button) findViewById(R.id.HomeButton);

        mResultList = (ListView) findViewById(R.id.peopleList);
        //mResultList.setHasFixedSize(true);
        //mResultList.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        final String Keyword = intent.getStringExtra("key");
        Log.i("bbbbbbbbb", Keyword);

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("users");
        //final ArrayList<book> bookLists = new ArrayList<>();
        Log.i("bbbbbbbbb", "kkkkk");

        // eventListener for searching book title's keyword

        ValueEventListener eventListener = new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean found;
                // String search = "Elements";
                //String search = Keyword;

                //String search = "Trevor Hastie Robert Tibshirani Jerome Friedman";
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    NormalUser user = ds.getValue(NormalUser.class);
                    Log.i("bbbbbbbbb", Boolean.toString(user==null));
                    String userName = user.getName();
                    Log.i("bbbbbbbbb","uuusseerr"+ userName);
                    String email = user.getEmail();
                    Log.i("bbbbbbbbb", "eemmaiill"+email);
                    Log.i("bbbbbbbbb", "pppppppp");
                    //check if user's name contains keyword
                    found = userName.contains(Keyword);
                    Log.i("bbbbbbbbb", "hhhhhhh");
                    if (found) {
                        users.add(user);
                    }
                    Log.i("bbbbbbbbb", "gggggg");




                    String size = Integer.toString(users.size());
                    Log.i("bbbbbbbbb", "lllllll");
                    adapter.notifyDataSetChanged();
                    Log.i("bbbbbbbbb", size);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        usersRef.addListenerForSingleValueEvent(eventListener);











        newSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchResultForPeople.this, Search.class);
                startActivity(intent);
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchResultForPeople.this, home_page.class);
                startActivity(intent);
            }
        });


        users = new ArrayList<>();
        adapter = new SearchPersonAdapter(this, users);
        mResultList.setAdapter(adapter);

    }

/*
    private void firebaseUserSearch (String searchText) {
        Toast.makeText(SearchResultForPeople.this, "Start Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase.orderByChild("name").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerOptions<NormalUser> options = new FirebaseRecyclerOptions.Builder<NormalUser>().setQuery(firebaseSearchQuery, NormalUser.class).setLifecycleOwner(this).build();
        FirebaseRecyclerAdapter<NormalUser, SearchResultForPeople.UserViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<NormalUser, UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull NormalUser model) {

                holder.user_Name.setText(model.getName());
                holder.user_Email.setText(model.getEmail());
            }

            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_search_result_for_people, viewGroup, false);
                SearchResultForPeople.UserViewHolder UviewHolder = new SearchResultForPeople.UserViewHolder(view);
                return UviewHolder;
            }
        };




        mResultList.setAdapter(firebaseRecyclerAdapter);



    }



    public static class UserViewHolder extends RecyclerView.ViewHolder{
        View mView;
        TextView user_Name,user_Email;
        public UserViewHolder(View itemView){
            super(itemView);
            mView= itemView;

            user_Name= (TextView) itemView.findViewById(R.id.InputName) ;
            user_Email = (TextView) itemView.findViewById(R.id.InputEmail);
        }

        public void setDetails(Context ctx, String userName, String userEmail, Image profileImage){

            TextView user_Name= (TextView) mView.findViewById(R.id.InputName) ;
            TextView user_Email = (TextView) mView.findViewById(R.id.Stat);
            //ImageView profile_image = (ImageView) mView.findViewById(R.id.profileImage);


            user_Name.setText(userName);
            user_Email.setText(userEmail);


            //Glide.with(ctx).load(profileImage).into(profile_image);

        }
    }
*/





}
