package com.example.softteam.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softteam.R;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.MyViewHolder>{
private final List<chatUser> chatUser;
private final Context context;

    public chatAdapter(List<com.example.softteam.data.chatUser> chatUser, Context context) {
        this.chatUser = chatUser;
        this.context = context;
    }

    @NonNull
    @Override
    public chatAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewGroup) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_adapter,null));
    }

    @Override
    public void onBindViewHolder(@NonNull chatAdapter.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return chatUser.size();
    }
    static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
