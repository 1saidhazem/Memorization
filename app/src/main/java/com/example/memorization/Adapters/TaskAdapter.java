package com.example.memorization.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.Task;
import com.example.memorization.databinding.CustomTasksBinding;
import com.example.memorization.onClick;

import java.text.SimpleDateFormat;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;
    onClick<Task> listener;
    String nameStudent;

    public TaskAdapter(List<Task> tasks,onClick<Task> listener,String nameStudent) {
        this.tasks = tasks;
        this.listener = listener;
        this.nameStudent = nameStudent;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tasks, null, false);
        TaskAdapter.TaskViewHolder ViewHolder = new TaskAdapter.TaskViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.bind(task);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{

        CustomTasksBinding binding;
        Task task;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomTasksBinding.bind(itemView);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickItem(task);
                }
            });

        }

        void bind(Task task){
            this.task = task;
            SimpleDateFormat format=new SimpleDateFormat("HH:mm a");
            binding.customTasksTvTime.setText(format.format(task.getFromDate()));
            binding.customTasksTvRating.setText(String.valueOf(task.getRating()));
            binding.customTasksTvDetails.setText(task.getFeedbackOnRating());
            binding.customTasksTvStudent.setText(String.valueOf(task.getStudentReceiver())); //

        }
    }
}
