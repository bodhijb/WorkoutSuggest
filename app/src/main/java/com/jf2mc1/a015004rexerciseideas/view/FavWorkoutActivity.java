package com.jf2mc1.a015004rexerciseideas.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jf2mc1.a015004rexerciseideas.R;
import com.jf2mc1.a015004rexerciseideas.fragment.FavWorkoutFragment;
import com.jf2mc1.a015004rexerciseideas.model.Workout;

public class FavWorkoutActivity extends AppCompatActivity
 {

    FavWorkoutFragment mFavWorkoutFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav_workout);
        setTitle(getString(R.string.faves_title));

        // instantiate fragment
        mFavWorkoutFragment = FavWorkoutFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .remove(mFavWorkoutFragment)
                .add(R.id.fav_workout_container, mFavWorkoutFragment)
                .commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }


}