package com.example.todo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private List<Task> taskList = new ArrayList<>();
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadTasks();

        taskAdapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onEditClick(int position) {
                editTask(position);

            }

            @Override
            public void onDeleteClick(int position) {
                deleteTask(position);
            }

            @Override
            public void onCheckboxClick(int position) {
                Task task = taskList.get(position);
                task.setCompleted(!task.isCompleted());
                dbHelper.updateTask(task);
                taskAdapter.notifyItemChanged(position);
            }
        });
        recyclerView.setAdapter(taskAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> addTask());
    }
    private void loadTasks() {
        taskList.clear();
//        taskList = dbHelper.getAllTasks();
        taskList.addAll(dbHelper.getAllTasks());
//        taskAdapter.notifyDataSetChanged();


    }
    private void addTask() {
        // Show dialog to add a new task
        showTaskDialog(-1);
    }

    private void editTask(int position) {
        // Show dialog to edit the selected task
        showTaskDialog(position);
    }

    private void deleteTask(int position) {
        Task task = taskList.get(position);
        dbHelper.deleteTask(task);
        taskList.remove(position);
        taskAdapter.notifyItemRemoved(position);
    }

    private void showTaskDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(position == -1 ? "Add Task" : "Edit Task");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        if (position != -1) {
            input.setText(taskList.get(position).getName());
        }
        builder.setView(input);

        builder.setPositiveButton("Save", (dialog, which) -> {
            String taskName = input.getText().toString();
            if (position == -1) {
                taskList.add(new Task(taskName));
                Task newTask = new Task(taskName);
                dbHelper.addTask(newTask);
                taskAdapter.notifyItemInserted(taskList.size() - 1);
            } else {
                taskList.get(position).setName(taskName);
                Task existingTask = taskList.get(position);
                existingTask.setName(taskName);
                dbHelper.updateTask(existingTask); // Cập nhật trong cơ sở dữ liệu
                taskAdapter.notifyItemChanged(position); // Thông báo cho adapter
            }
            taskAdapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}