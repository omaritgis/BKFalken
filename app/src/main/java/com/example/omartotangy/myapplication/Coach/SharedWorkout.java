package com.example.omartotangy.myapplication.Coach;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omartotangy.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * @author Omar Totangy
 * This class allows the user to create a workout, and upload it to the database.
 *
 * A simple {@link Fragment} subclass.
 */
public class SharedWorkout extends Fragment {
    private PublicWorkout workout = new PublicWorkout();
    private Spinner spinn;
    private EditText antal;
    private ListView list;
    private Button add, setName;
    private ArrayList<String> entries = new ArrayList<>();
    private ArrayAdapter<String> fullEntries;
    private View v;
    private DatabaseReference trainingRef;
    private int rounds = 0;
    private DatabaseReference excerciseRef;
    private ArrayList<String> excerciseList = new ArrayList<>();

    public SharedWorkout() {
        // Required empty public constructor
    }

    public void connectToDatabase(){
        excerciseRef = FirebaseDatabase.getInstance().getReference("excercises");
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                excerciseList = new ArrayList<>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    excerciseList.add(postSnapshot.getKey().toString());
                }
                drawSpinner();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(getApplicationContext(), "Fel grejer", Toast.LENGTH_LONG).show();
            }
        };
        excerciseRef.addValueEventListener(postListener);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_shared_workout, container, false);
        spinn = v.findViewById(R.id.choice);
        list = v.findViewById(R.id.listview);
        add = v.findViewById(R.id.repSetter);
        setName = v.findViewById(R.id.namnSetter);
        antal = v.findViewById(R.id.antal);
        antal.setHint(R.string.ronder);

        connectToDatabase();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWorkout();
            }
        });
        setName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setName();
            }
        });
        return v;
    }

    /**
     * This method allows the user to add excercises to the workout and checks weather someone is trying to add a excersice without any rounds.
     */
    public void addWorkout(){
        String text = antal.getText().toString();
        String text4 = antal.getHint().toString();
        String text2 = spinn.getSelectedItem().toString();
        //String text3 = text2 + " " + text + " " + text4;
        String text3 = text2 + " " + text;

        /*entries.add(text3);

        fullEntries = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,entries);
        list.setAdapter(fullEntries);
        workout.setPas(text3);*/
        if (text.contentEquals("")) {
            Toast.makeText(getActivity(), "Fyll i hur många ronder!", Toast.LENGTH_LONG).show();
        } else {
            rounds = Integer.parseInt(text);
            entries.add(text3);
            fullEntries = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, entries);
            list.setAdapter(fullEntries);
            workout.setPas(text3);
        }
    }

    /**
     * This method sets the name of the workout.
     */
    public void setName(){
        EditText namn = v.findViewById(R.id.NamnTxt);
        String name = namn.getText().toString();

        if(name.contentEquals("")){
            Toast.makeText(getActivity(), "Du måste skriva in ett namn", Toast.LENGTH_LONG).show();
        }else{
            workout.setName(name);
            save();
        }
    }

    /**
     * This method uploads workouts to the database.
     */
    public void save(){
        trainingRef = FirebaseDatabase.getInstance().getReference("workouts");
        trainingRef.child(workout.getName()).setValue(workout.pass);
    }

    public void drawSpinner(){
        excerciseList.add("Lägg till ny");

        if(getActivity()!=null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, excerciseList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinn.setAdapter(adapter);
            spinn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (adapterView.getSelectedItem().toString().contentEquals("Lägg till ny")) {
                        makeDialog();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    public void makeDialog(){
            Dialog modern = new Dialog(getActivity());
            modern.setContentView(R.layout.new_excercise);
            Button starta = modern.findViewById(R.id.add);
            EditText header = modern.findViewById(R.id.namn);

            starta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(header.getText().toString().length() >0){
                        DatabaseReference trainingRef = FirebaseDatabase.getInstance().getReference("excercises");
                        trainingRef.child(header.getText().toString()).setValue(header.getText().toString());
                        Toast.makeText(getActivity(),"Träningstyp tillagd", Toast.LENGTH_LONG).show();
                        modern.dismiss();
                    }else {
                        Toast.makeText(getActivity(),"Fyll i textfältet för att lägga till ny träningsform", Toast.LENGTH_LONG).show();
                    }

                }
            });

            modern.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            modern.show();
    }
}
