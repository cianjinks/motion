package com.cianjinks.motion.Util;

import android.content.res.Resources;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewBottomOffset extends RecyclerView.ItemDecoration {
    private int offset;

    public RecyclerViewBottomOffset(int offset)
    {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int dataSize = state.getItemCount();
        int position = parent.getChildAdapterPosition(view);
        if (dataSize > 0 && position == dataSize - 1) {
            outRect.set(0, 0, 0, offset);
        } else {
            outRect.set(0, 0, 0, 0);
        }
    }
}
