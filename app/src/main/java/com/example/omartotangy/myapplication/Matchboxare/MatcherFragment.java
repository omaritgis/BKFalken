package com.example.omartotangy.myapplication.Matchboxare;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.omartotangy.myapplication.Coach.Users;
import com.example.omartotangy.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * @author Omar Totangy
 * This class is used to show the user their fight results and to keep track of them and their weight.
 * The data is fetched from the database.
 * A simple {@link Fragment} subclass.
 */
public class MatcherFragment extends Fragment {
    TextView namn, vinster, förluster, oavgjort, matcherregistrerade, weight;
    Button registrera;
    String email;
    private Users thisUser;
    int i = 0;

    private DatabaseReference userRef, record;
    private ArrayList<Users> usersList = new ArrayList<>();

    public void connectDatabase() {
        userRef = FirebaseDatabase.getInstance().getReference("users");

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Users grejer = dataSnapshot.getKey("oma)
                Users post = null;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    post = postSnapshot.getValue(Users.class);
                    usersList.add(post);
                    if(post.getEmail().contentEquals(email)){
                        namn.setText(post.getName() + " " + post.getLastname());
                        weight.setText("" + post.getWeight());
                        vinster.setText(""+post.getWins());
                        förluster.setText(""+post.getLosses());
                        oavgjort.setText(""+post.getDraws());
                        thisUser = post;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(getApplicationContext(), "Fel grejer", Toast.LENGTH_LONG).show();
            }
        };

        userRef.addListenerForSingleValueEvent(postListener);
    }

    public MatcherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_matcher, container, false);
        namn = v.findViewById(R.id.namn);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            email = bundle.getString("epost");
        }
        weight = v.findViewById(R.id.weight);
        vinster = v.findViewById(R.id.wins);
        förluster = v.findViewById(R.id.losses);
        oavgjort = v.findViewById(R.id.draw);
        connectDatabase();
        return v;
    }

    public void kollaOmManKanRegistreraSig(){
        /*
            I denna klass ska man kolla på databasen om det finns ett event i databasen

            Om det finns ett event

            userRef = FirebaseDatabase.getInstance().getReference("event");
            (i listenern)
                event = null;
                for(event e : datasnapshot){
                eventlist.add(e)
                    if(e.kanRegistrera == true){
                        kanRegistrera == true;
                        }
                }
                räknaOmHurMångaDagar(e.getDatum);


          public void räknaOmHurMångaDagar(String date){
            göra stringen till en int, om int är mellan 21-10 dagar, då
            checkIfIAlreadyRegistered();

          public void kollaOmJagÄrRegistrerad(){
            Om false = button.setEnabled(true);
          }
         */

    }


}
