package com.cianjinks.motion.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cianjinks.motion.Goal.Goal;
import com.cianjinks.motion.R;

import java.util.ArrayList;

public class GoalViewAdapter extends RecyclerView.Adapter<GoalViewAdapter.ViewHolder> {

    protected GoalViewAdapter.GoalRecyclerViewListener mOnClickListener;
    private ArrayList<Goal> goals;
    private Context mContext;

    public interface GoalRecyclerViewListener {
        void onRecyclerViewClick(int pos);
        void onBinButtonClick(int pos);
        void onEditButtonClick(int pos);
    }

    public GoalViewAdapter(GoalRecyclerViewListener listener, Context context, ArrayList<Goal> goals)
    {
        this.goals = goals;
        this.mContext = context;
        this.mOnClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView cGoalEntryName;
        private final TextView cGoalEntryDesc;
        private final ImageButton cEditButton;

        private final ImageButton cBinButton;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            cGoalEntryName = view.findViewById(R.id.cGoalEntryName);
            cGoalEntryDesc = view.findViewById(R.id.cGoalEntryDesc);
            cEditButton = view.findViewById(R.id.cEditButton);
            cBinButton = view.findViewById(R.id.cBinButton);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onRecyclerViewClick(getAdapterPosition());
        }

        public TextView getGoalEntryName() { return cGoalEntryName; }
        public TextView getGoalEntryDesc() { return cGoalEntryDesc; }
        public ImageButton getEditButton() { return cEditButton; }
        public ImageButton getBinButton() { return cBinButton; }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_recyclerview_element, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getGoalEntryName().setText(goals.get(position).goalName);
        holder.getGoalEntryDesc().setText(goals.get(position).goalDesc);
        holder.getBinButton().setOnClickListener(v -> mOnClickListener.onBinButtonClick(position));
        holder.getEditButton().setOnClickListener(v -> mOnClickListener.onEditButtonClick(position));
    }

    @Override
    public int getItemCount() {
        if(goals == null) return 0;
        return goals.size();
    }
}
