package com.example.todoapp;

import android.view.Menu;
import android.view.MenuInflater;

import androidx.fragment.app.Fragment;


import java.util.UUID;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID taskId = (UUID) getIntent().getSerializableExtra(TaskListFragment.KEY_EXTRA_TASK_ID);
        return TaskFragment.newInstance(taskId);
    }


}

