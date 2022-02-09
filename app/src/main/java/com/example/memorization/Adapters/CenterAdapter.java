package com.example.memorization.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Center;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.databinding.CustomMemorizationCentersBinding;
import com.example.memorization.onClick;
import java.util.List;

public class CenterAdapter extends RecyclerView.Adapter<CenterAdapter.AddNewCenterAdapterViewHolder> {

    private List<Center> centers;
    private onClickAddNewCenter listener;
    private onClickListGroup clickListGroup;
    private onClick<Integer> listenerMap;

    public CenterAdapter(List<Center> centers, onClickAddNewCenter listener,onClickListGroup clickListGroup,onClick<Integer> listenerMap) {
        this.centers = centers;
        this.listener = listener;
        this.clickListGroup = clickListGroup;
        this.listenerMap = listenerMap;
    }

    public List<Center> getCenters() {return centers;}
    public void setCenters(List<Center> centers) {
        this.centers = centers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AddNewCenterAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_memorization_centers , null , false);
        AddNewCenterAdapterViewHolder ViewHolder = new AddNewCenterAdapterViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddNewCenterAdapterViewHolder holder, int position) {
        Center center = centers.get(position);
        holder.bind(center);
    }

    @Override
    public int getItemCount() {
        return centers.size();
    }


    class AddNewCenterAdapterViewHolder extends RecyclerView.ViewHolder{

        CustomMemorizationCentersBinding binding;
        Center center;
        int id;
        public AddNewCenterAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomMemorizationCentersBinding.bind(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickedAddNewCenter(center,id);
                    notifyDataSetChanged();
                }
            });

            binding.customMemoGroupLinearListGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListGroup.onClicked(id,center);
                    notifyDataSetChanged();
                }
            });

            binding.customMemoIvMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listenerMap.onClickItem(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });

        }
        public void bind(Center center) {
            this.center = center;
            this.id = center.getId();

            if (center.getLogo() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(center.getLogo() ,0 , center.getLogo().length);
                binding.customMemoCentersImg.setImageBitmap(bitmap);
            } else {
                // صورة افتراضية
                binding.customMemoCentersImg.setImageResource(R.drawable.ic_default_logo_center);
            }
            if (!center.getName().isEmpty() && !center.getName().equals("")) {
                binding.customMemoCentersTvName.setText(center.getName());
            }
            if (!center.getBranch().isEmpty() && !center.getBranch().equals("")){
                binding.customMemoCentersTvBranch.setText(center.getBranch());
            }

        }
    }
}
