package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.R;
import com.example.todo.Task;

import java.util.List;


public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> taskList;

    private OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
        void onCheckboxClick(int position);
    }

    public TaskAdapter(List<Task> taskList, OnTaskClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;

    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskName.setText(task.getName());
        holder.checkBox.setChecked(task.isCompleted());

        holder.checkBox.setOnClickListener(v -> listener.onCheckboxClick(position));
        holder.itemView.setOnClickListener(v -> listener.onEditClick(position)); // Sửa để đảm bảo nhấp vào toàn bộ item mở chỉnh sửa
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        CheckBox checkBox;
        ImageButton editButton, deleteButton;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
            checkBox = itemView.findViewById(R.id.checkBox_completed);
            editButton.setOnClickListener(v -> listener.onEditClick(getAdapterPosition()));
            deleteButton.setOnClickListener(v -> listener.onDeleteClick(getAdapterPosition()));
        }
    }



}


