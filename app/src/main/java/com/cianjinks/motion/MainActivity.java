package com.cianjinks.motion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;

import com.cianjinks.motion.Goal.AddGoalDialog;
import com.cianjinks.motion.Goal.Goal;
import com.cianjinks.motion.Util.GoalViewAdapter;
import com.cianjinks.motion.Util.LocalDateAdapter;
import com.cianjinks.motion.Util.RecyclerViewBottomOffset;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AddGoalDialog.GoalDialogListener, GoalViewAdapter.GoalRecyclerViewListener {

    public static String GOAL_DATA_FILE = "goaldata.json";
    protected RecyclerView mRecyclerView;
    protected MaterialToolbar mAppBar;
    protected ArrayList<Goal> goals;
    protected GoalViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        // Load Goals
        String goalsJSON = "{}";
        try {
            goalsJSON = Goal.loadGoalsFromInternalStorage(this.openFileInput(GOAL_DATA_FILE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Goals JSON is not empty
        if(!goalsJSON.equals("{}")) {
            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
            goals = gson.fromJson(goalsJSON, new TypeToken<ArrayList<Goal>>() {
            }.getType());
        }
        else
        {
            goals = new ArrayList<>();
        }

        // AppBar
        mAppBar = findViewById(R.id.cAppBar);
        mAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.menuPlus)
                {
                    DialogFragment frag = new AddGoalDialog();
                    frag.show(getSupportFragmentManager(), "addgoalfragment");
                    return true;
                }
                return false;
            }
        });

        // RecyclerView
        mRecyclerView = findViewById(R.id.cRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new GoalViewAdapter(this,this, goals);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new RecyclerViewBottomOffset((int)getResources().getDimension(R.dimen.goal_recyclerview_bottom_offset)));
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, Goal goal) {
        goals.add(goal);
        try {
            Goal.writeGoalsToInternalStorage(this.openFileOutput(GOAL_DATA_FILE, Context.MODE_PRIVATE), goals);
        } catch (FileNotFoundException e) {}
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // Do nothing if goal creation cancelled
    }

    @Override
    public void onRecyclerViewClick(int pos) {
        Intent intent = new Intent(this, GoalActivity.class);
        String message = goals.get(pos).goalName;
        intent.putExtra("com.cjink.initialapp.GOALNAME", message);
        startActivity(intent);
    }
}