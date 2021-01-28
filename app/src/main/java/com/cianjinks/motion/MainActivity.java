package com.cianjinks.motion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.cianjinks.motion.Dialog.AddGoalDialog;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    protected RecyclerView mRecyclerView;
    protected MaterialToolbar mAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        // AppBar
        mAppBar = findViewById(R.id.cAppBar);
        mAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.menuPlus)
                {
                    DialogFragment frag = new AddGoalDialog();
                    frag.show(getSupportFragmentManager(), "addgoalfragment");
                    // Handle goal add
                    return true;
                }
                return false;
            }
        });

        // RecyclerView
        mRecyclerView = findViewById(R.id.cRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);



    }
}