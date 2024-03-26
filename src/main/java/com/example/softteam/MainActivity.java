package com.example.softteam;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softteam.messages.MessageAdapter;
import com.example.softteam.messages.MessageList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private final List<MessageList> messageLists = new ArrayList<>();
    private String number;
    private boolean dataset =false;

    private int unseen=0;
    private String last="";
    private String chatKey="";
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReferenceFromUrl("https://softteam-88b9b-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = getIntent().getStringExtra("Mobiles");
        if (number == null) {
            // Handle the case where 'number' is null
            return;
        }

        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(number);

        final CircleImageView userprofile = findViewById(R.id.userprofile);
        recyclerView = findViewById(R.id.messageRecycleView);
        String getNumber = getIntent().getStringExtra("mobile");
        String email = getIntent().getStringExtra("email");
        String name = getIntent().getStringExtra("name");


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messageAdapter=new MessageAdapter(messageLists,MainActivity.this);
        recyclerView.setAdapter(messageAdapter);
        //noinspection deprecation
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        //noinspection deprecation
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String profile = snapshot.child("profile").getValue(String.class);
                if (profile != null && !profile.isEmpty()) {
                    Picasso.get().load(profile).into(userprofile);
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                // Handle onCancelled event
                Toast.makeText(MainActivity.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.child("users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageLists.clear();
                unseen = 0;
                last = "";
                chatKey="";
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    final String getMobile = dataSnapshot.getKey();
                    final String getName = dataSnapshot.child("name").getValue(String.class);
                    final String getProfile = dataSnapshot.child("profile").getValue(String.class);
                    MessageList messageList = new MessageList(getName, getMobile, "", getProfile,unseen,chatKey);
                    messageLists.add(messageList);
                }
                recyclerView.setAdapter(new MessageAdapter(messageLists, MainActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled event
                progressDialog.dismiss();
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messageLists.clear();
                for (DataSnapshot ignored : dataSnapshot.child("users").getChildren()) {
                    final String getMobile = dataSnapshot.getKey();
                   dataset=false;
                    assert getMobile != null;
                    if (getMobile.equals(number)) {
                        final String getName = dataSnapshot.child("name").getValue(String.class);
                        final String getProfilePic = dataSnapshot.child("name").getValue(String.class);

                        databaseReference.child("chat");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                int getchatcount = (int) dataSnapshot.getChildrenCount();

                                if (getchatcount > 0) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        final String getkey = dataSnapshot1.getKey();
                                        chatKey = getkey;
                                        if (dataSnapshot1.hasChild("user1") && dataSnapshot1.hasChild("user2")) {
                                            dataSnapshot1.hasChild("messages");
                                        }


                                        final String getuser1 = dataSnapshot1.child("user_1").getValue(String.class);
                                        final String getuser2 = dataSnapshot1.child("user_2").getValue(String.class);
                                        assert getuser1 != null;

                                        if (getuser1.equals(number) && Objects.equals(getuser2, number)) {
                                            for (DataSnapshot chatDataSnapshot : dataSnapshot1.child("messages").getChildren()) {
                                                final long getMsgkey = Long.parseLong(Objects.requireNonNull(chatDataSnapshot.getKey()));
                                                final long getlstMsg = Long.parseLong(MemoryData.getLastMsgTs(getkey, MainActivity.this));
                                                last = chatDataSnapshot.child("msg").getValue(String.class);

                                                if (getMsgkey > getlstMsg) {
                                                    unseen++;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (!dataset) {
                                    MessageList messageList = new MessageList(getName, getMobile, last, getProfilePic, unseen, chatKey);
                                    messageLists.add(messageList);
                                    messageAdapter.updatedata(messageLists);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
