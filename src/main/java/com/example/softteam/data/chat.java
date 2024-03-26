package com.example.softteam.data;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softteam.MemoryData;
import com.example.softteam.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class chat extends AppCompatActivity {
    private final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://softteam-88b9b-default-rtdb.firebaseio.com/");
    private String chatKey;
    String getUserMobile="";
    private RecyclerView chattingRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        final ImageView backbtn=findViewById(R.id.backline);
        final TextView name=findViewById(R.id.textView2);
        final CircleImageView profile=findViewById(R.id.profilePic);
        final ImageView sent=findViewById(R.id.sentbtn);

        chattingRecycler=findViewById(R.id.chattingRecycler);

        final String getName=getIntent().getStringExtra("name");
        final String getProfile=getIntent().getStringExtra("profile");
        chatKey=getIntent().getStringExtra("Chat key");
       final String getMobile=getIntent().getStringExtra("mobile");
getUserMobile= MemoryData.getData(chat.this);
name.setText(getName);
        Picasso.get().load(getProfile).into(profile);

        chattingRecycler.setHasFixedSize(true);
        chattingRecycler.setLayoutManager(new LinearLayoutManager(chat.this));

        if (chatKey.isEmpty()){
  databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
         chatKey="1";
          if (dataSnapshot.hasChild("chat")){
              chatKey=String.valueOf(dataSnapshot.child("child").getChildrenCount() +1);
          }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
  });
        }

sent.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final String getTxtMessage=name.getText().toString();
      final String currentTimestamp=String.valueOf(System.currentTimeMillis()).substring(0,10);
        MemoryData.saveLastMsgTS(currentTimestamp,chatKey,chat.this);
databaseReference.child("chat").child(chatKey).child("user1").setValue(getUserMobile);
databaseReference.child("chat").child(chatKey).child("user2").setValue(getMobile);
databaseReference.child("chat").child(chatKey).child("messages").child(currentTimestamp).child("msg").setValue(getTxtMessage);
databaseReference.child("chat").child(chatKey).child("messages").child("mobile").child(currentTimestamp).setValue(getUserMobile);

//edit text use for message edit
        name.setText("");

    }
});
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}