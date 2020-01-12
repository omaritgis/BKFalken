package com.example.omartotangy.myapplication.Matchboxare;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omartotangy.myapplication.Coach.PublicWorkout;
import com.example.omartotangy.myapplication.PublicClasses.ExpandableListViewAdapter;
import com.example.omartotangy.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Omar Totangy
 * This class shows an expandable listview of the public workouts. The public workouts is fetched from the database.
 * Can show a alertdialog on longclick
 * Can start the rondTimer activity from the dialog
 *
 */
public class ImportedWorkouts extends AppCompatActivity {

    private DatabaseReference trainRef;
    private ArrayList<PublicWorkout> pass = new ArrayList<>();
    private ArrayList<String> listan = new ArrayList<>();
    private ArrayList<String> rubriker = new ArrayList<>();
    private ExpandableListView elv;
    private HashMap<String, ArrayList<String>> childList = new HashMap<>();
    private ExpandableListViewAdapter elvAdapter;
    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imported_workouts);
        elv = findViewById(R.id.expandables);
        connectDatabase();
    }

    public void connectDatabase(){
        trainRef = FirebaseDatabase.getInstance().getReference("workouts");
        i = 0;
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PublicWorkout post = null;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    rubriker.add(postSnapshot.getKey());

                    for(DataSnapshot snap : postSnapshot.getChildren()){
                        //post.pass = snap.getValue(PublicWorkout.class);
                        listan.add(snap.getValue(String.class));
                        childList.put(rubriker.get(i),listan);
                    }
                    i++;
                }
                createLayout(childList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Fel grejer", Toast.LENGTH_LONG).show();
            }
        };

        trainRef.addListenerForSingleValueEvent(postListener);
    }

    public void createLayout(HashMap<String, ArrayList<String>> childListt){
        elvAdapter = new ExpandableListViewAdapter(this, childListt, rubriker);
        elv.setAdapter(elvAdapter);

        elv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                long packedPosition = elv.getExpandableListPosition(i);

                int itemType = ExpandableListView.getPackedPositionType(packedPosition);
                int groupPosition = ExpandableListView.getPackedPositionGroup(packedPosition);
                int childPosition = ExpandableListView.getPackedPositionChild(packedPosition);

                if(itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP){
                    modernDialog(groupPosition);
                }

                return false;
            }
        });

        }

    public void startWorkout(int groupposition){
        Intent intent = new Intent(this, RondTimer.class);
        int i = 0;
        for(String s : listan){
            intent.putExtra("träning" + i, s);
            i++;
        }
        startActivity(intent);
    }

    public void modernDialog(int groupPosition){
        Dialog modern = new Dialog(this);
        modern.setContentView(R.layout.workout_options_box);
        String title = elv.getExpandableListAdapter().getGroup(groupPosition).toString();
        Button starta = modern.findViewById(R.id.startWorkout);
        Button radera = modern.findViewById(R.id.deleteWorkout);
        TextView header = modern.findViewById(R.id.excercize);
        header.setText(title);
        radera.setEnabled(false);// Detta får fixas senare

        listan = elvAdapter.children.get(title);

        //View view = getLayoutInflater().inflate(R.layout.workout_options_box, null);





        starta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWorkout(groupPosition);
            }
        });

        radera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                    Radera träningspasset
                    Skriv om filen men write istället för append
                 */
            }
        });

        modern.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        modern.show();

        /*mBuilder.setView(view);
        AlertDialog box = mBuilder.create();
        box.show();*/

    }
    }

