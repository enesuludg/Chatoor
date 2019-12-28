package com.example.chatoor.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatoor.MessageActivity;
import com.example.chatoor.R;
import com.example.chatoor.StartActivity;
import com.example.chatoor.model.User;

import java.util.List;

public class UserAdater extends RecyclerView.Adapter<UserAdater.ViewHolder> {

    private Context mContext;
    private List<User> mUser;
    private boolean ischat;

    public UserAdater(Context mContext , List<User> mUser, boolean ischat){

        this.mUser = mUser;
        this.mContext = mContext;
        this.ischat= ischat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
        return new  UserAdater.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final User user =mUser.get(position);
        holder.username.setText(user.getUsername());
        if (user.getImageURL().equals("default")){

            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{

            Glide.with(mContext).load(user.getImageURL()).into(holder.profile_image);
        }

        if (ischat){
            if (user.getStatus().equals("online")){
                holder.img_on.setVisibility(View.VISIBLE);
                holder.img_off.setVisibility(View.GONE);

            }else {
                holder.img_on.setVisibility(View.GONE);
                holder.img_off.setVisibility(View.VISIBLE);
            }
        }else {
            holder.img_on.setVisibility(View.GONE);
            holder.img_off.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext , MessageActivity.class);
                intent.putExtra("userid",user.getId());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mUser.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;
        private ImageView img_on;
        private ImageView img_off;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username =itemView.findViewById(R.id.username);
            profile_image= itemView.findViewById(R.id.profile_image);
            img_on =itemView.findViewById(R.id.img_on);
            img_off =itemView.findViewById(R.id.img_off);

        }
    }
}

/*

package com.example.chatoor.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatoor.Adapter.UserAdater;
import com.example.chatoor.R;
import com.example.chatoor.model.Chat;
import com.example.chatoor.model.Chatlist;
import com.example.chatoor.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdater userAdater;
    private List<User> mUsers;
    private List<Chatlist> userlist;
    FirebaseUser fuser;
    DatabaseReference reference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        userlist = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userlist.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chatlist chatlist =snapshot.getValue(Chatlist.class);
                    userlist.add(chatlist);
                }

                ChatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }

    private void ChatList() {
        mUsers = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);
                    for (Chatlist chatlist : userlist){
                        if (user.getId().equals(chatlist.getId())){
                            mUsers.add(user);
                        }
                    }
                }
                userAdater =new UserAdater(getContext(), mUsers);//true?
                recyclerView.setAdapter(userAdater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}

/*

/ oncreat

reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userlist.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if(chat.getSender().equals(fuser.getUid())){
                        userlist.add(chat.getReceiver());
                    }
                    if (chat.getReceiver().equals(fuser.getUid())){
                        userlist.add(chat.getSender());
                    }
                }
                /*Set<String> hashSet = new HashSet<String>(userlist);
                userlist.clear();
                userlist.addAll(hashSet); */
/*

    readChats();
}

@Override
public void onCancelled(@NonNull DatabaseError databaseError) {

        }
        });

private void  readChats(){
        mUsers = new ArrayList<>();
        reference =FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user =snapshot.getValue(User.class);

                    for (String id : userlist) {
                        if (user.getId().equals(id)){

                            if (mUsers.size() != 0) {

                                for (User user1 : mUsers) {

                                    if (!user.getId().equals(user1.getId())) {

                                        mUsers.add(user);
                                      }
                                 }
                            }else {
                                mUsers.add(user);
                            }
                        }
                    }
                }

                userAdater = new UserAdater(getContext(), mUsers);
                recyclerView.setAdapter(userAdater);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    for (int i = 0; i< mUser.size(); i++) {
    User user1 = mUser.get(i);
    if (!user.getId().equals(user1.getId())){
       mUser.add(user);

    } // If the existing list don't have same value for sender and reciever
} //



 */




















