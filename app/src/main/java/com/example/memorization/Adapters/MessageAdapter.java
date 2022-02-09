package com.example.memorization.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Message;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.WalletGroup;
import com.example.memorization.databinding.CustomMessageStudentBinding;
import com.example.memorization.databinding.CustomWalletsBinding;
import com.example.memorization.onClick;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.WalletViewHolder> {

    private List<Message> messages;
    private onClick<Integer> listener;

    public MessageAdapter(List<Message> messages,onClick<Integer> listener) {
        this.messages = messages;
        this.listener=listener;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_wallets , null , false);
        WalletViewHolder ViewHolder = new WalletViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.bind(message,position);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class WalletViewHolder extends RecyclerView.ViewHolder{

        CustomMessageStudentBinding binding;
        Message message;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomMessageStudentBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickItem(getAdapterPosition());
                }
            });
        }

        public void bind(Message message,int position) {
            this.message = message;

            binding.customMessageTvBody.setText(message.getMessageBody());
            binding.customMessageTvSender.setText(String.valueOf(message.getSenderId()));
        }
    }
}
