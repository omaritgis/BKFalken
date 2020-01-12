package com.example.omartotangy.myapplication.Matchboxare;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.omartotangy.myapplication.R;


/**
 * A simple {@link Fragment} subclass.
 * @author Omar Totangy
 *
 * This class will give the boxer three choices, to open public workouts, to open private workouts, to create new workouts.
 * All three choices send the user into a different activity.
 */
public class WorkoutFragment extends Fragment {
    private Button visa, skapadePass, skapaPass;
    private openWorkoutsActivities callBack;

    /**
     * Used as a callback method for the activity "matchBoxareFrag"
     */
    public interface openWorkoutsActivities{
        void openImportedWorkouts();
        void openCreatedWorkouts();
        void openCreateWorkouts();
    }

    public WorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if( context instanceof openWorkoutsActivities )
            this.callBack = (openWorkoutsActivities) context;
        else
            throw new ExceptionInInitializerError();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_workout, container, false);
        visa = view.findViewById(R.id.visa);
        visa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.openImportedWorkouts();
            }
        });

        skapadePass = view.findViewById(R.id.skapade);
        skapadePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.openCreatedWorkouts();
            }
        });
        skapaPass = view.findViewById(R.id.skapa);
        skapaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.openCreateWorkouts();
            }
        });

        return view;
    }

}
