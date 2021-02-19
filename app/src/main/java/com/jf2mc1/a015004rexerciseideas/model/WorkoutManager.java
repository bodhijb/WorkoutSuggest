package com.jf2mc1.a015004rexerciseideas.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorkoutManager {

    private Context mContext;
    SharedPreferences mSharedPreferences;

    public WorkoutManager(Context context) {
        mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }


    public void saveWorkout(Workout workout) {

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(workout);
        editor.putString(workout.getDesc(), json);
        editor.apply();
        Log.i("SAVED", "workout saved");

    }

    public void deleteWorkout(Workout workout) {
        if (mSharedPreferences.contains(workout.getDesc())) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.remove(workout.getDesc())
                    .commit();
            Log.i("DELETED", "workout del");
        }
    }

    public List<Workout> retrieveWorkout() {

        Map<String, ?> data = mSharedPreferences.getAll();
        List<Workout> workoutList = new ArrayList<>();

        for(Map.Entry<String, ?> entry : data.entrySet()) {

            if(entry.getKey().matches("variations_seed_native_stored"))
                continue;

            Gson gson = new Gson();
            String json = mSharedPreferences.getString(entry.getKey(), "");
            Workout workout = gson.fromJson(json, Workout.class);
            workoutList.add(workout);

        }
        return workoutList;
    }


}
