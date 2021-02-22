package com.cianjinks.motion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.cianjinks.motion.Goal.Goal;
import com.google.android.material.appbar.MaterialToolbar;
import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.model.CalendarMonth;
import com.kizitonwose.calendarview.ui.DayBinder;
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder;
import com.kizitonwose.calendarview.ui.ViewContainer;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.Locale;

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

        Context context = this;
        CalendarView calendarView = findViewById(R.id.cGoalCalendar);

        // Setup day binder
        calendarView.setDayBinder(new DayBinder<DayViewContainer>() {
            @NotNull
            @Override
            public DayViewContainer create(@NotNull View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(@NotNull DayViewContainer dayViewContainer, @NotNull CalendarDay calendarDay) {
                dayViewContainer.textView.setText(String.valueOf(calendarDay.getDate().getDayOfMonth()));
                dayViewContainer.textView.setTextColor(ContextCompat.getColor(context, R.color.light_gray));
                //dayViewContainer.layout.setBackground(null);
            }
        });

        // Setup month header binder
        calendarView.setMonthHeaderBinder(new MonthHeaderFooterBinder<MonthHeaderContainer>() {

            @NotNull
            @Override
            public MonthHeaderContainer create(@NotNull View view) {
                return new MonthHeaderContainer(view);
            }

            @Override
            public void bind(@NotNull MonthHeaderContainer monthHeaderContainer, @NotNull CalendarMonth calendarMonth) {
                if(monthHeaderContainer.legendLayout != null) {
                    if (monthHeaderContainer.legendLayout.getTag() == null) {
                        monthHeaderContainer.legendLayout.setTag(calendarMonth.getMonth());
                    }
                }
            }
        });

        calendarView.setMonthScrollListener(
                calendarMonth -> {
                    TextView tv = findViewById(R.id.cGoalCalendarMonth);
                    String title = new DateFormatSymbols().getMonths()[calendarMonth.getMonth()-1] + " " + calendarMonth.getYear();
                    tv.setText(title);
                    return null;
                }
        );

//          TODO
//        ImageView nextMonth = findViewById(R.id.exFiveNextMonthImage);
//        nextMonth.setOnClickListener(v -> {
//            calendarView.smoothScrollToMonth(calendarView.findFirstVisibleMonth().getYearMonth().plusMonths(1));
//        });
//
//        ImageView previousMonth = findViewById(R.id.exFivePreviousMonthImage);
//        previousMonth.setOnClickListener(v -> {
//            calendarView.smoothScrollToMonth(calendarView.findFirstVisibleMonth().getYearMonth().minusMonths(1));
//        });

        YearMonth currentMonth = YearMonth.now();
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        calendarView.setup(currentMonth.minusMonths(13), currentMonth.plusMonths(1), firstDayOfWeek);
        calendarView.scrollToMonth(currentMonth);
    }

    public static class DayViewContainer extends ViewContainer
    {
        public TextView textView;
        public FrameLayout layout;
        public DayViewContainer(View view) {
            super(view);
            textView = view.findViewById(R.id.cGoalCalendarDayText);
            layout = view.findViewById(R.id.cGoalCalendarDayFrame);
        }
    }

    public static class MonthHeaderContainer extends ViewContainer {

        public LinearLayout legendLayout;
        public MonthHeaderContainer(View view) {
            super(view);
            legendLayout = view.findViewById(R.id.cLegendLayout);
        }
    }
}
