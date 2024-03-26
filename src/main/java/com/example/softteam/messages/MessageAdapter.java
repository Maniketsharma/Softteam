package com.example.softteam.messages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softteam.R;
import com.example.softteam.data.chat;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{
private List<MessageList>messageLists;
private final Context context;

    public MessageAdapter(List<MessageList> messageLists, Context context) {
        this.messageLists = messageLists;
        this.context = context;
    }

    @NonNull
    @Override
    public  MessageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.messageapter_layout,parent,false);
        return  new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder myViewHolder, int p) {
        MessageList message = messageLists.get(p);

        // Load profile image if the URL is not empty
        if (!message.getProfileUrl().isEmpty()) {
            Picasso.get().load(message.getProfileUrl()).into(myViewHolder.profile);
        }

        // Bind data to views
        myViewHolder.name.setText(message.getName());
        myViewHolder.lastMessage.setText(message.getLastMessage());

        // Hide the unseen message TextView if there are no unseen messages
        if (message.getUnseenMessages() == 0) {
            myViewHolder.unseenMessage.setVisibility(View.GONE);
            myViewHolder.lastMessage.setTextColor(Color.parseColor("#959595"));
        } else {
            myViewHolder.unseenMessage.setVisibility(View.VISIBLE);
            myViewHolder.unseenMessage.setText(message.getUnseenMessages()+"");
            myViewHolder.lastMessage.setTextColor(context.getResources().getColor(R.color.theme_color_80));
        }

        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent=new Intent(context, chat.class);
                 intent.putExtra("mobile",message.getMobile());
                 intent.putExtra("name",message.getName());
                 intent.putExtra("profile",message.getProfileUrl());
                 intent.putExtra("chat Key",message.getChatKey());
                 context.startActivity(intent);
            }
        });
    }
@SuppressLint("NotifyDataSetChanged")
public void updatedata(List<MessageList> messageLists){
        this.messageLists=messageLists;
        notifyDataSetChanged();
}

    @Override
    public int getItemCount() {
        return messageLists.size();
    }

    static  class MyViewHolder extends RecyclerView.ViewHolder{
        private final CircleImageView profile;
        private final TextView name;
        private final TextView lastMessage;
        private final TextView unseenMessage;
        private final LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profile=itemView.findViewById(R.id.profilePic);
            name=itemView.findViewById(R.id.name);
            lastMessage=itemView.findViewById(R.id.lastMessages);
            unseenMessage=itemView.findViewById(R.id.unseenMessages);
            linearLayout=itemView.findViewById(R.id.linearLayout);
        }
    }

}
