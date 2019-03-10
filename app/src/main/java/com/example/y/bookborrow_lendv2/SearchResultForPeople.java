package com.example.y.bookborrow_lendv2;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchResultForPeople extends AppCompatActivity {

    private EditText mSearchWord;
    private Button mSearchButton;

    private RecyclerView mResultList;
    private DatabaseReference mUserDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result_for_people);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("users");
        mSearchWord = (EditText) findViewById(R.id.editText);
        mSearchButton = (Button) findViewById(R.id.See_Result_of_PersonButton);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));


        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchWord.getText().toString();

                firebaseUserSearch(searchText);
            }
        });
    }


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




    @Override
    protected void onStart(){
        super.onStart();
        //SearchBookAdapter.startListening();


    }


    public void peopleNewSearch(View view){


    }

    public void peopleBackHome(View view){

    }

    public void displayPeopleName(View view){

    }

    public void displayPeopleImage(View view){

    }

    public void displayPeopleInfo(View view){

    }
}
