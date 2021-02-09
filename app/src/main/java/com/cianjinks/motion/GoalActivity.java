package com.cianjinks.motion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cianjinks.motion.Goal.Goal;
import com.google.android.material.appbar.MaterialToolbar;

import java.time.LocalDate;

public class GoalActivity extends AppCompatActivity {

    protected MaterialToolbar mGoalAppBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        // Testing passing in the goal as an intent
        Intent intent = getIntent();
        Goal goal = (Goal)intent.getSerializableExtra(MainActivity.GOAL_INTENT);
        TextView test = findViewById(R.id.cGoalActivityTest);
        test.setText(goal.goalName);

        mGoalAppBar = findViewById(R.id.cGoalAppBar);
        mGoalAppBar.setTitle(goal.goalName);
        mGoalAppBar.setNavigationOnClickListener(v -> finish());
    }
}
