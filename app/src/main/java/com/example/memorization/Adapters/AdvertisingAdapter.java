package com.example.memorization.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorization.RoomDatabase.Advertising;
import com.example.memorization.R;
import com.example.memorization.databinding.CustomAdvertisingBinding;
import com.example.memorization.onClick;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AdvertisingAdapter extends RecyclerView.Adapter<AdvertisingAdapter.AdvertisingViewHolder> {

    private List<Advertising> advertisingList;
    private onClick listener;

    public AdvertisingAdapter(List<Advertising> advertisingList,onClick listener) {
        this.advertisingList = advertisingList;
        this.listener = listener;
    }

    public void setAdvertisingList(List<Advertising> advertisingList) {
        this.advertisingList = advertisingList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdvertisingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_advertising, null, false);
        AdvertisingAdapter.AdvertisingViewHolder ViewHolder = new AdvertisingAdapter.AdvertisingViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertisingViewHolder holder, int position) {
        Advertising advertising = advertisingList.get(position);
        holder.bind(advertising);
    }

    @Override
    public int getItemCount() {
        return advertisingList.size();
    }


    class AdvertisingViewHolder extends RecyclerView.ViewHolder {

        CustomAdvertisingBinding binding;
        Advertising advertising;

        public AdvertisingViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomAdvertisingBinding.bind(itemView);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickItem(getAdapterPosition());
                }
            });
        }

        public void bind(Advertising advertising) {
            this.advertising = advertising;

            if (advertising.getDate() != 0) {
                long time = advertising.getDate() * (long) 1000;
                Date date = new Date(time);
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                format.setTimeZone(TimeZone.getTimeZone("EGY"));
                String timeStr = format.format(date);
                binding.customAdvertisingTvPublishDate.setText("منذ "+timeStr);
            }
            if (!advertising.getAdvertisingBody().isEmpty() && !advertising.getAdvertisingBody().equals("")) {
                binding.customAdvertisingTvMessageBody.setText(advertising.getAdvertisingBody());
            }
            if (!advertising.getSender().isEmpty() && !advertising.getSender().equals("")){
                binding.customAdvertisingTvPublisherName.setText(advertising.getSender());
            }else {
                binding.customAdvertisingTvPublisherName.setText("...");
            }

        }
    }
}
