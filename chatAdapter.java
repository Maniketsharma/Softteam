package com.example.softteam.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softteam.MemoryData;
import com.example.softteam.R;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.MyViewHolder>{
private  List<chatUser> chatUser;
private final Context context;
private String userMobile;
    public chatAdapter(List<com.example.softteam.data.chatUser> chatUser, Context context) {
        this.chatUser = chatUser;
        this.context = context;
        this.userMobile= MemoryData.getData(context);
    }

    @NonNull
    @Override
    public chatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter,null));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull chatAdapter.MyViewHolder holder, int position) {

    chatUser list2=chatUser.get(position);

        if(list2.getMobile().equals(userMobile)){
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.openLayout.setVisibility(View.GONE);


            holder.mymsg.setText(list2.getMessage());
            holder.myTm.setText(list2.getDate()+" "+list2.getDate());
        }else {

            holder.myLayout.setVisibility(View.GONE);
            holder.openLayout.setVisibility(View.VISIBLE);

            holder.oppomsge.setText(list2.getMessage());
            holder.myTm.setText(list2.getDate()+" "+list2.getDate());

        }
    }

    @Override
    public int getItemCount() {
        return chatUser.size();
    }

    public void updateChatUser(List<chatUser> chatUsers){
        this.chatUser=chatUsers;

    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
       private final LinearLayout openLayout;
        private final LinearLayout myLayout;
       private final TextView oppomsge;
        private final TextView mymsg;
        private final TextView myTm;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            openLayout=itemView.findViewById(R.id.openlayout);
            myLayout=itemView.findViewById(R.id.mylayout);
            oppomsge=itemView.findViewById(R.id.chatopomsg);
            mymsg=itemView.findViewById(R.id.mymsg);
            TextView optm = itemView.findViewById(R.id.oppomsgtm);
            myTm=itemView.findViewById(R.id.mymsgtm);
        }
    }
}
