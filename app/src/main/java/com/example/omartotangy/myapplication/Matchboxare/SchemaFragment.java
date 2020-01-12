package com.example.omartotangy.myapplication.Matchboxare;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.omartotangy.myapplication.PublicClasses.Blankett;
import com.example.omartotangy.myapplication.PublicClasses.ExpandableListViewAdapter;
import com.example.omartotangy.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @author Kasim Mirzada
 * This class fetches data from firebase and presents it in an expandable listview.
 *
 * A simple {@link Fragment} subclass.
 */
public class SchemaFragment extends Fragment {

    private DatabaseReference schema;
    private ArrayList<Blankett> blankettList = new ArrayList<Blankett>();
    private ExpandableListView elv;
    private ArrayList<String> listan = new ArrayList<>();
    private ArrayList<String> rubriker = new ArrayList<>();
    private HashMap<String, ArrayList<String>> childList = new HashMap<>();
    private ExpandableListViewAdapter elvAdapter;
    private int i = 0;

    public SchemaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_schema, container, false);
        elv = v.findViewById(R.id.expandables);
        connectToDatabase();

        return v;
    }


    public void connectToDatabase(){
        schema = FirebaseDatabase.getInstance().getReference("event");

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Blankett post = null;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    rubriker.add("" + postSnapshot.getKey());

                    for(DataSnapshot snap : postSnapshot.getChildren()){
                        try {
                            post = snap.getValue(Blankett.class);
                            listan.add("Datum: " + post.getDatum());
                            listan.add("Plats: " + post.getNamn());
                            listan.add("Start Tid: " + post.getStartTid());
                            listan.add("Slut Tid: " + post.getSlutTid());
                            listan.add("-----------------------------");
                            childList.put(rubriker.get(i), listan);
                        }catch (DatabaseException de){
                            //Toast.makeText(getActivity(), "Event inte ifyllt korrekt, tränaren bör fylla i alla textfält.", Toast.LENGTH_LONG).show();
                        }
                    }
                    listan = new ArrayList<>();
                    i++;
                }
                createLayout(childList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(getApplicationContext(), "Fel grejer", Toast.LENGTH_LONG).show();
            }
        };

        schema.addListenerForSingleValueEvent(postListener);
    }

    /**
     * Create the listview containing a custom layout
     * id of the textview and what is entered
     * Uses an arrayadapter to achieve the results
     * @param childListt HashMap of string and string arraylist
     */

    public void createLayout(HashMap<String, ArrayList<String>> childListt){
        elvAdapter = new ExpandableListViewAdapter(getActivity(), childListt, rubriker);
        elv.setAdapter(elvAdapter);

    }

}
