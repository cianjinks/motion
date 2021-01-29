package com.cianjinks.motion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cianjinks.motion.Goal.Goal;

public class GoalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);

        // Testing passing in the goal as an intent
        Intent intent = getIntent();
        Goal goal = (Goal)intent.getSerializableExtra(MainActivity.GOAL_INTENT);
        TextView test = findViewById(R.id.cGoalActivityTest);
        test.setText(goal.goalName);
    }
}
