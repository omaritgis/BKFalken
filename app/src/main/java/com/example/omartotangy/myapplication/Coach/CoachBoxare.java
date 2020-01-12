package com.example.omartotangy.myapplication.Coach;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omartotangy.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * @author Omar Totangy
 * This fragment collects a list of boxers. The user can increase fightresults to each boxer in that list.
 *
 * A simple {@link Fragment} subclass.
 */
public class CoachBoxare extends Fragment {
    private ListView lView;
    private ArrayList<String> boxarLista = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private DatabaseReference userRef;
    private ArrayList<Users> usersList = new ArrayList<Users>();
    private Users user;

    public CoachBoxare() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_coach_boxare, container, false);
        lView = v.findViewById(R.id.listboxer);
        getBoxers();
        return v;
    }

    /**
     * This method connects to the database and fetches userdata if they are fighters and not coaches.
     */
    public void getBoxers(){
        userRef = FirebaseDatabase.getInstance().getReference("users");

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users post = null;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    post = postSnapshot.getValue(Users.class);
                    if (post.type.contentEquals("Matchboxare")) {
                            usersList.add(post);
                            boxarLista.add(post.getName() + " " + post.getLastname());
                        }

                }
                setlView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(getApplicationContext(), "Fel grejer", Toast.LENGTH_LONG).show();
            }
        };

        userRef.addListenerForSingleValueEvent(postListener);


    }

    /**
     * This method creates a listview that contains a list of boxers, when an item is clicked the user gets a dialog shown.
     */
    public void setlView(){
        //adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,boxarLista);
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.cardview_listview,R.id.namn,boxarLista);
        lView.setAdapter(adapter);
        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                moderDialog(i);
            }
        });
    }

    /**
     * This method creates a dialog that shows user wins, losses and draws and give the coach
     * @param position position on the listview that is clicked
     */
    public void createDialog(int position){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View view = getLayoutInflater().inflate(R.layout.dialogbox, null);
        Button plusVinster = view.findViewById(R.id.plusVinster);
        Button minusVinster = view.findViewById(R.id.minusVinster);
        Button plusFörluster = view.findViewById(R.id.plusFörluster);
        Button plusOavgjort = view.findViewById(R.id.plusOavgjort);
        Button minusFörluster = view.findViewById(R.id.minusFörluster);
        Button minusOavgjort = view.findViewById(R.id.minusOavgjort);
        Button spara = view.findViewById(R.id.spara);

        String title = lView.getItemAtPosition(position).toString();
        mBuilder.setTitle(title);
        TextView vinst, förlust, oavgjort;

        vinst = view.findViewById(R.id.wins);
        förlust = view.findViewById(R.id.losses);
        oavgjort = view.findViewById(R.id.oavgjort);

        vinst.setText("" +usersList.get(position).getWins());
        förlust.setText("" +usersList.get(position).getLosses());
        oavgjort.setText("" + usersList.get(position).getDraws());


        plusVinster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int vinstnr = usersList.get(position).getWins();
                vinstnr++;
                usersList.get(position).setWins(vinstnr);
                vinst.setText("" + usersList.get(position).getWins());



            }
        });

        minusVinster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int vinstnr = usersList.get(position).getWins();
                if(vinstnr >0){
                vinstnr--;
                usersList.get(position).setWins(vinstnr);
                vinst.setText("" + usersList.get(position).getWins());
                }
            }
        });

        plusFörluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int förlustnr = usersList.get(position).getLosses();
                förlustnr++;
                usersList.get(position).setLosses(förlustnr);
                förlust.setText("" + usersList.get(position).getLosses());
            }
        });

        minusFörluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int förlustnr = usersList.get(position).getLosses();
                if(förlustnr > 0){
                förlustnr--;
                usersList.get(position).setLosses(förlustnr);
                förlust.setText("" + usersList.get(position).getLosses());
                }
            }
        });

        plusOavgjort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oavgjortnr = usersList.get(position).getDraws();
                oavgjortnr++;
                usersList.get(position).setDraws(oavgjortnr);
                oavgjort.setText("" + usersList.get(position).getDraws());
            }
        });

        minusOavgjort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oavgjortnr = usersList.get(position).getDraws();
                if(oavgjortnr > 0){
                    oavgjortnr--;
                    usersList.get(position).setDraws(oavgjortnr);
                    oavgjort.setText("" + usersList.get(position).getDraws());
                }
            }
        });

        spara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uppdateraDatabasen(position);
            }
        });

        mBuilder.setView(view);
        mBuilder.setPositiveButton("Avbryt", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog box = mBuilder.create();
        box.show();
    }

    /**
     * This method uppdates the wins, losses and draws that the user sets.
     * @param position position on the item clicked in the listview
     */
    public void uppdateraDatabasen(int position){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        user = usersList.get(position);
        mDatabase.child(usersList.get(position).getName()).setValue(user);
    }

    public void moderDialog(int position){
        Dialog modern = new Dialog(getActivity());
        modern.setContentView(R.layout.dialogbox);

        Button plusVinster = modern.findViewById(R.id.plusVinster);
        Button minusVinster = modern.findViewById(R.id.minusVinster);
        Button plusFörluster = modern.findViewById(R.id.plusFörluster);
        Button plusOavgjort = modern.findViewById(R.id.plusOavgjort);
        Button minusFörluster = modern.findViewById(R.id.minusFörluster);
        Button minusOavgjort = modern.findViewById(R.id.minusOavgjort);
        Button spara = modern.findViewById(R.id.spara);

        TextView titel = modern.findViewById(R.id.title);
        TextView vikt = modern.findViewById(R.id.weight);

        String title = lView.getItemAtPosition(position).toString();
        titel.setText(title);
        TextView vinst, förlust, oavgjort;

        vinst = modern.findViewById(R.id.wins);
        förlust = modern.findViewById(R.id.losses);
        oavgjort = modern.findViewById(R.id.oavgjort);

        vinst.setText("" +usersList.get(position).getWins());
        förlust.setText("" +usersList.get(position).getLosses());
        oavgjort.setText("" + usersList.get(position).getDraws());
        vikt.setText(""+ usersList.get(position).getWeight());


        plusVinster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int vinstnr = usersList.get(position).getWins();
                vinstnr++;
                usersList.get(position).setWins(vinstnr);
                vinst.setText("" + usersList.get(position).getWins());



            }
        });

        minusVinster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int vinstnr = usersList.get(position).getWins();
                if(vinstnr >0){
                    vinstnr--;
                    usersList.get(position).setWins(vinstnr);
                    vinst.setText("" + usersList.get(position).getWins());
                }
            }
        });

        plusFörluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int förlustnr = usersList.get(position).getLosses();
                förlustnr++;
                usersList.get(position).setLosses(förlustnr);
                förlust.setText("" + usersList.get(position).getLosses());
            }
        });

        minusFörluster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int förlustnr = usersList.get(position).getLosses();
                if(förlustnr > 0){
                    förlustnr--;
                    usersList.get(position).setLosses(förlustnr);
                    förlust.setText("" + usersList.get(position).getLosses());
                }
            }
        });

        plusOavgjort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oavgjortnr = usersList.get(position).getDraws();
                oavgjortnr++;
                usersList.get(position).setDraws(oavgjortnr);
                oavgjort.setText("" + usersList.get(position).getDraws());
            }
        });

        minusOavgjort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oavgjortnr = usersList.get(position).getDraws();
                if(oavgjortnr > 0){
                    oavgjortnr--;
                    usersList.get(position).setDraws(oavgjortnr);
                    oavgjort.setText("" + usersList.get(position).getDraws());
                }
            }
        });

        spara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uppdateraDatabasen(position);
            }
        });

        modern.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        modern.show();

    }

}
