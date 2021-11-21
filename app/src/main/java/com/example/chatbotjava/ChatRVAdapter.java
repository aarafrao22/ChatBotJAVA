package com.example.chatbotjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatRVAdapter extends RecyclerView.Adapter {
    private ArrayList<ChatModel> chatsModelArrayList;
    private Context context;

    public ChatRVAdapter(ArrayList<ChatModel> chatsModelArrayList, Context context) {
        this.chatsModelArrayList = chatsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType){
            case '0':
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_chat,parent,false);
                UserViewHolder userViewHolder = new UserViewHolder(view);
                return userViewHolder;

            case '1':
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_robot_chat,parent,false);
                BotViewHolder botViewHolder = new BotViewHolder(view);
                return botViewHolder;

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatModel chatModel = chatsModelArrayList.get(position);
        switch (chatModel.getSender()){
            case "user":
                ((UserViewHolder)holder).UserTextView.setText(chatModel.getMessage());
                break;

            case "bot":
                ((BotViewHolder)holder).BotTextView.setText(chatModel.getMessage());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return chatsModelArrayList.size();
    }

    @Override
    public long getItemId(int position) {
        switch (chatsModelArrayList.get(position).getSender()){
            case "user":
                return 0;

            case "bot":
                return 1;

            default:
                return -1;
        }
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        TextView UserTextView;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            UserTextView = itemView.findViewById(R.id.userTV);
        }
    }

    public static class BotViewHolder extends RecyclerView.ViewHolder{
        TextView BotTextView;

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            BotTextView = itemView.findViewById(R.id.robotTV);
        }
    }
}
