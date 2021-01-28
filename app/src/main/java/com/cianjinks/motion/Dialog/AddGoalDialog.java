package com.cianjinks.motion.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cianjinks.motion.R;

public class AddGoalDialog extends DialogFragment {

    protected View mView;
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.add_goal_dialog, container, false);
//        return mView;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.Theme_Motion));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.add_goal_dialog, null);

        builder.setView(mView);
        builder.setTitle(R.string.goal_dialog_title);
        builder.setPositiveButton(R.string.goal_dialog_confirm, (dialog, which) -> {

        })
        .setNegativeButton(R.string.goal_dialog_cancel, (dialog, which) -> {

        });
        return builder.create();
    }
}
