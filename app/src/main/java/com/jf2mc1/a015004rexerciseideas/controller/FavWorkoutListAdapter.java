package com.jf2mc1.a015004rexerciseideas.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jf2mc1.a015004rexerciseideas.R;
import com.jf2mc1.a015004rexerciseideas.model.Workout;
import com.jf2mc1.a015004rexerciseideas.view.FavWorkoutViewHolder;
import java.util.List;

public class FavWorkoutListAdapter extends RecyclerView.Adapter<FavWorkoutViewHolder> {

    private List<Workout> mWorkoutList;
    private Context mContext;

    public FavWorkoutListAdapter(List<Workout> workoutList, Context context) {
        mWorkoutList = workoutList;
        mContext = context;
    }

    @NonNull
    @Override
    public FavWorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fav_workout_item, parent, false);

        return new FavWorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavWorkoutViewHolder holder, int position) {

        String workoutType = mWorkoutList.get(position).getType().toUpperCase();
        holder.getTxtFavItemType().setText(workoutType);

        String workoutDesc = mWorkoutList.get(position).getDesc();
        holder.getTxtFavItemDesc().setText(workoutDesc);

        holder.getBtnFavItemShare().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // call up list of apps to share with
                // create an ACTION_SEND INTENT
                Intent intent = new Intent(
                        android.content.Intent.ACTION_SEND);
                // This will be the actual content you wish to share
                String shareContent = workoutDesc;
                // the type of the content is text
                intent.setType("text/plain");
                // applying information subject and body
                intent.putExtra(Intent.EXTRA_SUBJECT,
                        "Nice workout!");
                intent.putExtra(Intent.EXTRA_SUBJECT, shareContent);
                // fire
                mContext.startActivity(
                        Intent.createChooser(intent, "Share Via"));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mWorkoutList.size();
    }

    public List<Workout> getWorkoutList() {
        return mWorkoutList;
    }

    public Context getContext() {
        return mContext;
    }
}
