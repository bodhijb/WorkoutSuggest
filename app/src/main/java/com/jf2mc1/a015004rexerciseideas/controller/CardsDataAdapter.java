package com.jf2mc1.a015004rexerciseideas.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.jf2mc1.a015004rexerciseideas.R;
import com.jf2mc1.a015004rexerciseideas.model.Workout;

public class CardsDataAdapter extends ArrayAdapter<Workout> {

    private Context mContext;
    private boolean clicked = true;
    private Workout mWorkout;
    private LikedWorkoutListener mLikedWorkoutListener;


    public CardsDataAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        mContext = context;
        mLikedWorkoutListener = (LikedWorkoutListener) context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Workout workout = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main,
                    parent, false);
        }

        TextView tvType = convertView.findViewById(R.id.tv_type);
        String typeText = getItem(position).getType();
        tvType.setText(typeText);

        TextView tvDesc = convertView.findViewById(R.id.tv_desc);
        String descText = getItem(position).getDesc();
        tvDesc.setText(descText);

        ImageButton likeButton = convertView.findViewById(
                R.id.like_button);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go to fav workout activity
                if(clicked) {
                    // change to filled img
                    likeButton.setImageResource(R.drawable.liked);
                    clicked = !clicked;

                    // add animation to buttons
                    YoYo.with(Techniques.BounceIn)
                            .duration(700)
                            .playOn(likeButton);

                    // assign selected workout variable
                    mWorkout = getItem(position);
                    mWorkout.setLiked(true);

                } else {
                    // was previously like, now click to unlike
                    likeButton.setImageResource(R.drawable.not_liked);
                    clicked = !clicked;
                    // assign selected workout variable
                    mWorkout = getItem(position);
                    mWorkout.setLiked(false);

                }
                // tell listeners exercise was liked
                mLikedWorkoutListener.likedWorkout(mWorkout);
            }
        });




        return convertView;
    }
}
