package com.jf2mc1.a015004rexerciseideas.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.jf2mc1.a015004rexerciseideas.R;
import com.jf2mc1.a015004rexerciseideas.controller.FavWorkoutListAdapter;
import com.jf2mc1.a015004rexerciseideas.model.Workout;
import com.jf2mc1.a015004rexerciseideas.model.WorkoutManager;

import java.util.ArrayList;
import java.util.List;


public class FavWorkoutFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FavWorkoutListAdapter mFavWorkoutListAdapter;
    private WorkoutManager mWorkoutManager;
    private List<Workout> mWorkoutList = new ArrayList<>();

    private Workout deletedWorkout;


    public FavWorkoutFragment() {
        // Required empty public constructor
    }


    public static FavWorkoutFragment newInstance() {
        FavWorkoutFragment fragment = new FavWorkoutFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // use joke manager to get jokes from shared prefs
        mWorkoutManager = new WorkoutManager(context);
        mWorkoutList.clear();

        if(mWorkoutManager.retrieveWorkout().size() > 0) {
            for(Workout workout: mWorkoutManager.retrieveWorkout()) {
                mWorkoutList.add(workout);
            }
        }





    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // initialize view and UIs
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // create the view (FrameLO). inflate the fragment layout in the arg container
        View view = inflater.inflate(R.layout.fragment_fav_workout,
                container, false);

        // instantiate UIs and Adapter
        if(view != null) {
            mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_workout_frag);
            mFavWorkoutListAdapter = new FavWorkoutListAdapter(
                    mWorkoutList, getContext());
            mRecyclerView.setAdapter(mFavWorkoutListAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(
                    getContext()));


            // add swipe to delete feature to recyclerview
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                    mSimpleCallback);
            itemTouchHelper.attachToRecyclerView(mRecyclerView);

        }

        return view;
    }

    ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper
            .SimpleCallback(0, ItemTouchHelper.LEFT
            | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();
            switch (direction) {

                case ItemTouchHelper.LEFT:
                case ItemTouchHelper.RIGHT:

                    deletedWorkout = mWorkoutList.get(position);
                    mWorkoutManager.deleteWorkout(mWorkoutList.get(position));
                    mWorkoutList.remove(position);
                    mFavWorkoutListAdapter.notifyItemRemoved(position);
                    mFavWorkoutListAdapter.notifyDataSetChanged();

                    // allow user to undo deletion
                    Snackbar.make(mRecyclerView, "Exercise is \"Removed\"",
                            Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mWorkoutList.add(position, deletedWorkout);
                                    mWorkoutManager.saveWorkout(deletedWorkout);
                                    mFavWorkoutListAdapter.notifyItemInserted(position);
                                }
                            }).show();
                    break;
            }

        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public Workout getDeletedWorkout() {
        return deletedWorkout;
    }





}