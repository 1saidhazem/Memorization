package com.example.memorization.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorization.R;
import com.example.memorization.RoomDatabase.CenterGroup;
import com.example.memorization.RoomDatabase.Group;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.databinding.CustomGroupMemorizationBinding;
import com.example.memorization.onClick;

import java.io.ByteArrayInputStream;
import java.util.List;

public class GroupMemorizationAdapter extends RecyclerView.Adapter<GroupMemorizationAdapter.groupMemorizationViewHolder> {

    private List<Group> groups;
    private onClickAddNewGroup listener;
    private onClickListWallet listenerListWallet;
    private onClick<Integer> listenerListStudent;

    public GroupMemorizationAdapter(List<Group> groups, onClickAddNewGroup listener, onClickListWallet listenerListWallet, onClick<Integer> listenerListStudent) { //String branchName,
        this.groups = groups;
        this.listener = listener;
        this.listenerListWallet = listenerListWallet;
        this.listenerListStudent = listenerListStudent;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public groupMemorizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_group_memorization, null, false);
        groupMemorizationViewHolder ViewHolder = new groupMemorizationViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull groupMemorizationViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.bind(group, position);
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }


    class groupMemorizationViewHolder extends RecyclerView.ViewHolder {

        CustomGroupMemorizationBinding binding;
        Group group;

        public groupMemorizationViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomGroupMemorizationBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickedAddNewGroup(group, getItemCount());
                    notifyDataSetChanged();
                }
            });

            binding.customGroupLinearListWallets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listenerListWallet.onClicked(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
            binding.customGroupLinearListStudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listenerListStudent.onClickItem(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

        }

        public void bind(Group group, int position) {
            this.group = group;

//            if (group.getLogo() != null) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(group.getLogo() ,0 , group.getLogo().length);
//                binding.customGroupImg.setImageBitmap(bitmap);
//            } else {
                // صورة افتراضية
                binding.customGroupImg.setImageResource(R.drawable.ic_default_logo_center);
//            }

            binding.customGroupTvName.setText(group.getName());
            binding.customGroupTvCenterName.setText(group.getCenterName());
            binding.customGroupTvSupervisor.setText(String.valueOf(group.getIdentificationNumberGroup()));
            binding.customGroupTvBranch.setText(group.getBranch());

        }
    }
}
