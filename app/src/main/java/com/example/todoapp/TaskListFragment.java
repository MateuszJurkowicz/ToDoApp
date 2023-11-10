package com.example.todoapp;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class TaskListFragment extends Fragment {

    public RecyclerView recyclerView;
    public TaskAdapter adapter;
    public static final String KEY_EXTRA_TASK_ID = "task_id";

    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nameTextView;
        public TextView dateTextView;
        public ImageView iconImageView;
        public CheckBox isDoneView;
        private Task task;

        public TaskHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_task, parent, false));
            itemView.setOnClickListener(this);
            nameTextView = itemView.findViewById(R.id.task_item_name);
            dateTextView = itemView.findViewById(R.id.task_item_date);
            iconImageView = itemView.findViewById(R.id.task_item_iconImage);
            isDoneView = itemView.findViewById(R.id.task_item_isDone);

        }
        public void bind(Task task){
            this.task = task;
            String taskName = task.getName();
            if (taskName.length() > 25) {
                nameTextView.setText(TextUtils.ellipsize(taskName, nameTextView.getPaint(), nameTextView.getWidth(), TextUtils.TruncateAt.END));
            } else {
                nameTextView.setText(taskName);
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm dd.MM.yyyy");
            String formattedDate = dateFormat.format(task.getDate());
            dateTextView.setText(formattedDate);
            if(task.getCategory().equals(Category.HOME))
            {
                iconImageView.setImageResource(R.drawable.ic_house);
            }
            else
            {
                iconImageView.setImageResource(R.drawable.ic_university);
            }
            if(task.isDone()) {
                nameTextView.setPaintFlags(nameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                dateTextView.setPaintFlags(dateTextView.getPaintFlags() | (Paint.STRIKE_THRU_TEXT_FLAG));
            }
            else{
                nameTextView.setPaintFlags(nameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                dateTextView.setPaintFlags(dateTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
        }
        public CheckBox getCheckBox()
        {
            return isDoneView;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.putExtra(KEY_EXTRA_TASK_ID, task.getId());
            startActivity(intent);
        }
    }
    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {
        private List<Task> tasks;
        public TaskAdapter(List<Task> tasks)
        {
            this.tasks = tasks;
        }
        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int ViewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }
        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position){
            Task task = tasks.get(position);
            CheckBox checkBox = holder.getCheckBox();
            checkBox.setChecked(tasks.get(position).isDone());
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) ->
                    tasks.get(holder.getBindingAdapterPosition()).setDone(isChecked));
            holder.bind(task);

        }
        public int getItemCount(){
            return tasks.size();
        }
    }

    private void updateView() {
        TaskStorage taskStorage = TaskStorage.getInstance();
        List<Task> tasks = taskStorage.getTasks();
        if(adapter == null) {
            adapter = new TaskAdapter(tasks);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);
        recyclerView = view.findViewById(R.id.task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }
}
