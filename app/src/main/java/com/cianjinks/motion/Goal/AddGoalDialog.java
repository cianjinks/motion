package com.cianjinks.motion.Goal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cianjinks.motion.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class AddGoalDialog extends DialogFragment {

    public interface GoalDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, Goal goal);
        void onDialogNegativeClick(DialogFragment dialog);
    }
    protected GoalDialogListener listener;
    protected View mView;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.Theme_Motion));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.add_goal_dialog, null);

        builder.setView(mView);
        builder.setTitle(R.string.goal_dialog_title);
        builder.setPositiveButton(R.string.goal_dialog_confirm, (dialog, which) -> {
            TextInputEditText cGoalName = mView.findViewById(R.id.cGoalName);
            TextInputEditText cGoalDesc = mView.findViewById(R.id.cGoalDesc);
            TextInputEditText cGoalRangeStart = mView.findViewById(R.id.cGoalRangeStart);
            TextInputEditText cGoalRangeEnd = mView.findViewById(R.id.cGoalRangeEnd);
            Goal goal = new Goal(
                    cGoalName.getText().toString(),
                    cGoalDesc.getText().toString(),
                    Integer.parseInt(cGoalRangeStart.getText().toString()),
                    Integer.parseInt(cGoalRangeEnd.getText().toString())
            );
            listener.onDialogPositiveClick(this, goal);
        })
        .setNegativeButton(R.string.goal_dialog_cancel, (dialog, which) -> {
            listener.onDialogNegativeClick(this);
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (GoalDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException("Activity must implement NoticeDialogListener");
        }
    }
}
