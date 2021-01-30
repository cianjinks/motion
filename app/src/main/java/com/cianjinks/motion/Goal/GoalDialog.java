
package com.cianjinks.motion.Goal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cianjinks.motion.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class GoalDialog extends DialogFragment {

    private static final String ERROR_MESSAGE = "Invalid Input";
    public interface GoalDialogListener {
        void onAddDialogPositiveClick(DialogFragment dialog, Goal goal);
        void onEditDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    protected GoalDialogListener listener;
    protected View mView;

    private final Goal mGoal;

    // Text Fields
    private TextInputEditText cGoalName;
    private TextInputEditText cGoalDesc;
    private TextInputEditText cGoalRangeStart;
    private TextInputEditText cGoalRangeEnd;

    // Text Field Layouts
    private TextInputLayout cGoalNameLayout;
    private TextInputLayout cGoalDescLayout;
    private TextInputLayout cGoalRangeStartLayout;
    private TextInputLayout cGoalRangeEndLayout;

    public GoalDialog(Goal goal)
    {
        this.mGoal = goal;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.Theme_Motion));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.add_goal_dialog, null);

        cGoalNameLayout = mView.findViewById(R.id.goalNameLayout);
        cGoalName = mView.findViewById(R.id.cGoalName);
        cGoalDescLayout = mView.findViewById(R.id.goalDescLayout);
        cGoalDesc = mView.findViewById(R.id.cGoalDesc);
        cGoalRangeStartLayout = mView.findViewById(R.id.goalRangeStartLayout);
        cGoalRangeStart = mView.findViewById(R.id.cGoalRangeStart);
        cGoalRangeEndLayout = mView.findViewById(R.id.goalRangeEndLayout);
        cGoalRangeEnd = mView.findViewById(R.id.cGoalRangeEnd);

        builder.setView(mView);
        if(mGoal != null)
        {
            cGoalName.setText(mGoal.goalName);
            cGoalDesc.setText(mGoal.goalDesc);
            cGoalRangeStart.setText(String.valueOf(mGoal.completionRangeStart));
            cGoalRangeEnd.setText(String.valueOf(mGoal.completionRangeEnd));
            builder.setTitle(R.string.goal_dialog_title_alternate);
        }
        else { builder.setTitle(R.string.goal_dialog_title); }
        builder.setPositiveButton(R.string.goal_dialog_confirm, (dialog, which) -> {
            // This is now left empty as we override it later on to control whether or not to close the dialog
//            TextInputEditText cGoalName = mView.findViewById(R.id.cGoalName);
//            cGoalName.setError("");
//            TextInputEditText cGoalDesc = mView.findViewById(R.id.cGoalDesc);
//            cGoalDesc.setError("");
//            TextInputEditText cGoalRangeStart = mView.findViewById(R.id.cGoalRangeStart);
//            TextInputEditText cGoalRangeEnd = mView.findViewById(R.id.cGoalRangeEnd);
//            if (cGoalName.getText() == null) {
//                cGoalName.setHighlightColor(ContextCompat.getColor(getActivity(), R.color.error_red));
//            } else if (cGoalDesc.getText() == null) {
//                cGoalDesc.setHighlightColor(ContextCompat.getColor(getActivity(), R.color.error_red));
//            } else if (cGoalRangeStart.getText() == null) {
//                cGoalRangeStart.setHighlightColor(ContextCompat.getColor(getActivity(), R.color.error_red));
//            } else if (cGoalRangeEnd.getText() == null) {
//                cGoalRangeEnd.setHighlightColor(ContextCompat.getColor(getActivity(), R.color.error_red));
//            } else {
//                Goal goal = new Goal(
//                        cGoalName.getText().toString(),
//                        cGoalDesc.getText().toString(),
//                        Integer.parseInt(cGoalRangeStart.getText().toString()),
//                        Integer.parseInt(cGoalRangeEnd.getText().toString())
//                );
//                listener.onDialogPositiveClick(this, goal);
//            }
        })
        .setNegativeButton(R.string.goal_dialog_cancel, (dialog, which) -> {
            listener.onDialogNegativeClick(this);
        });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            listener = (GoalDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement GoalDialogListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog dialog = (AlertDialog) getDialog();
        final DialogFragment df = this;
        final Context context = df.getActivity();
        if(dialog != null && context != null)
        {
            Button confirmButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
            confirmButton.setOnClickListener(v -> {
                cGoalNameLayout.setErrorEnabled(false);
                cGoalDescLayout.setErrorEnabled(false);
                cGoalRangeStartLayout.setErrorEnabled(false);
                cGoalRangeEndLayout.setErrorEnabled(false);
                boolean isError = false;
                if(cGoalName.getText().toString().length() == 0)
                {
                    cGoalNameLayout.setErrorEnabled(true);
                    cGoalNameLayout.setError(ERROR_MESSAGE);
                    isError = true;
                }
                if(cGoalDesc.getText().toString().length() == 0)
                {
                    cGoalDescLayout.setErrorEnabled(true);
                    cGoalDescLayout.setError(ERROR_MESSAGE);
                    isError = true;
                }
                if(cGoalRangeStart.getText().toString().length() == 0)
                {
                    cGoalRangeStartLayout.setErrorEnabled(true);
                    cGoalRangeStartLayout.setError(ERROR_MESSAGE);
                    isError = true;
                }
                if(cGoalRangeEnd.getText().toString().length() == 0)
                {
                    cGoalRangeEndLayout.setErrorEnabled(true);
                    cGoalRangeEndLayout.setError(ERROR_MESSAGE);
                    isError = true;
                }
                int goalMin = Integer.parseInt(cGoalRangeStart.getText().toString());
                int goalMax = Integer.parseInt(cGoalRangeEnd.getText().toString());
                if(goalMin >= goalMax)
                {
                    isError = true;
                    cGoalRangeStartLayout.setErrorEnabled(true);
                    cGoalRangeStartLayout.setError(ERROR_MESSAGE);
                    cGoalRangeEndLayout.setErrorEnabled(true);
                    cGoalRangeEndLayout.setError(ERROR_MESSAGE);
                }
                if(!isError) {
                    if(mGoal == null) {
                        Goal goal = new Goal(
                                cGoalName.getText().toString(),
                                cGoalDesc.getText().toString(),
                                goalMin,
                                goalMax

                        );
                        listener.onAddDialogPositiveClick(df, goal);
                    }
                    else {
                        mGoal.goalName = cGoalName.getText().toString();
                        mGoal.goalDesc = cGoalDesc.getText().toString();
                        mGoal.completionRangeStart = goalMin;
                        mGoal.completionRangeEnd = goalMax;
                        listener.onEditDialogPositiveClick(df);
                    }
                    dialog.dismiss();
                }
            });
        }
    }
}
