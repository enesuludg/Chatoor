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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdater userAdater;
    private List<User> mUsers;
    private List<String> usersList;
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
        usersList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                usersList.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    Chat chat=snapshot.getValue(Chat.class);

                    assert chat != null;
                    if (chat.getSender().equals(fuser.getUid())) {

                        usersList.add(chat.getReceiver());

                    }

                    if (chat.getReceiver().equals(fuser.getUid())) {

                        usersList.add(chat.getSender());

                    }

                }


                Set<String> hashSet = new HashSet<String>(usersList);
                usersList.clear();
                usersList.addAll(hashSet);


                readChats();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }


    private void readChats(){

        mUsers=new ArrayList<>();

        reference= FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //mUsers.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    User user=snapshot.getValue(User.class);

                    for(String id:usersList){

                        assert user != null;
                        if (user.getId().equals(id)) {

                            mUsers.add(user);

                        }

                    }

                }

                userAdater = new UserAdater(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdater);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}


    /*


                    if(!userlist.contains(chat.getReceiver()))userlist.add(chat.getReceiver());
                    if(!userlist.contains(chat.getSender()))userlist.add(chat.getSender());


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
    } */



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




















