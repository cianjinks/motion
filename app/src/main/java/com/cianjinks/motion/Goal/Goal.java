package com.cianjinks.motion.Goal;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;

public class Goal implements Serializable {

    public String goalName;
    public String goalDesc;

    public int completionRangeStart;
    public int completionRangeEnd;
    public boolean completed = false;

    public int startColor;
    public int endColor;
    public String colorRange;

    public LinkedHashMap<LocalDate, Integer> goalProgressData;

    public Goal(String goalName, String goalDesc, int completionRangeStart, int completionRangeEnd)
    {
        this.goalName = goalName;
        this.goalDesc = goalDesc;
        this.completionRangeStart = completionRangeStart;
        this.completionRangeEnd = completionRangeEnd;

        goalProgressData = new LinkedHashMap<LocalDate, Integer>();
    }

    public void addProgressDataPoint(LocalDate date, int value)
    {
        if(value >= completionRangeStart && value <= completionRangeEnd)
        {
            goalProgressData.put(date, value);
        }
    }

    public static String loadGoalsFromInternalStorage(FileInputStream input)
    {
        String jsonString = "{}";
        try (FileInputStream fis = input) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            jsonString = new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    public static boolean writeGoalsToInternalStorage(FileOutputStream output, List<Goal> goals)
    {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(goals);

        try (FileOutputStream fos = output) {
            fos.write(json.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
