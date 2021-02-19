package com.jf2mc1.a015004rexerciseideas.view;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.arasthel.asyncjob.AsyncJob;
import com.jf2mc1.a015004rexerciseideas.R;
import com.jf2mc1.a015004rexerciseideas.controller.CardsDataAdapter;
import com.jf2mc1.a015004rexerciseideas.controller.LikedWorkoutListener;
import com.jf2mc1.a015004rexerciseideas.model.Workout;
import com.jf2mc1.a015004rexerciseideas.model.WorkoutManager;
import com.wenchao.cardstack.CardStack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        CardStack.CardEventListener, LikedWorkoutListener{

    private CardStack mCardStack;
    private CardsDataAdapter mCardAdapter;
    private ArrayList<Workout> mAllWorkouts = new ArrayList<>();
    WorkoutManager mWorkoutManager;

    // the following are used for shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.main_title));

        mWorkoutManager = new WorkoutManager(this);

        mCardStack = findViewById(R.id.card_stack);
        mCardStack.setContentResource(R.layout.exercise_card);
        mCardStack.setStackMargin(20);
        mCardAdapter = new CardsDataAdapter(this, 0);
        //mCardStack.setAdapter(mCardAdapter);


        // initialize shake variables
        mSensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake(int count) {
                handleShakeEvent();
            }
        });


        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                    @Override
                    public Boolean doAsync() {
                        // Do some background work
                        // convert string to json object
                        try {
                            JSONObject rootObject = new JSONObject(loadJSONFromAsset());

                            JSONArray cardioExercises = (JSONArray) rootObject.get("cardio");
                            addExercisesToArrayList(cardioExercises, "CARDIO", mAllWorkouts);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<Boolean>() {
                    @Override
                    public void onResult(Boolean result) {
                        for (Workout workout : mAllWorkouts) {
                            mCardAdapter.add(workout);
                        }
                        mCardStack.setAdapter(mCardAdapter);
                    }
                }).create().start();


    }

    private void handleShakeEvent() {

        new AsyncJob.AsyncJobBuilder<Boolean>()
                .doInBackground(new AsyncJob.AsyncAction<Boolean>() {
                    @Override
                    public Boolean doAsync() {
                        Collections.shuffle(mAllWorkouts);
                        return true;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction() {
                    @Override
                    public void onResult(Object o) {
                        mCardAdapter.clear();
                        mCardAdapter = new CardsDataAdapter(
                                MainActivity.this, 0);
                        for (Workout workout : mAllWorkouts) {
                            mCardAdapter.add(workout);
                        }
                        mCardStack.setAdapter(mCardAdapter);
                    }
                }).create().start();


    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("workout.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void addExercisesToArrayList(JSONArray jsonArray, String exerciseType,
                                         List<Workout> arrayList) {
        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject json = new JSONObject(jsonArray.getString(i));
                    String desc = json.getString("desc");
//                    String statistics = json.getString("statistics");
//                    JSONObject name1 = json.getJSONObject("John");
//                    String ageJohn = name1.getString("Age");
                    arrayList.add(new Workout(exerciseType, desc));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    // CARDSTACK listener methods
    @Override
    public boolean swipeEnd(int section, float distance) {
        return distance > 300 ? true : false;
    }

    @Override
    public boolean swipeStart(int section, float distance) {
        return true;
    }

    @Override
    public boolean swipeContinue(int section, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void discarded(int mIndex, int direction) {

    }

    @Override
    public void topCardTapped() {

    }
    ///


    // MENU OPTIONS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(MainActivity.this,
                FavWorkoutActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void likedWorkout(Workout workout) {
        if (workout.getIsLiked() == true) {

            mWorkoutManager.saveWorkout(workout);
        }

    }


}