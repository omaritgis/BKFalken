package com.example.omartotangy.myapplication.Coach;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.omartotangy.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * @author Omar Totangy
 * This class allows the coach to register a new user. The data is stored later in firebase.
 *
 * A simple {@link Fragment} subclass.
 */
public class RegisterUser extends Fragment {
    private EditText namn, efternamn, epost, lösenord, ålder, vikt, längd;
    private Spinner kontotyp, stance;
    private Button register;
    private DatabaseReference mDatabase;

    public RegisterUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register_user, container, false);

        register = v.findViewById(R.id.register);
        namn = v.findViewById(R.id.firstname);
        efternamn = v.findViewById(R.id.lastname);
        epost = v.findViewById(R.id.epost);
        lösenord = v.findViewById(R.id.password);
        ålder = v.findViewById(R.id.age);
        vikt = v.findViewById(R.id.weight);
        längd = v.findViewById(R.id.lenght);

        kontotyp = v.findViewById(R.id.type);
        stance = v.findViewById(R.id.stance);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                samlaData();
            }
        });

        return v;
    }

    /**
     * This method gathers all data about the boxer or coach and uppdates the firebase with the new user.
     */
    public void samlaData(){

        int age = 0;
        int weight = 0;
        int lenght = 0;

        String name = namn.getText().toString();
        String efternamn = this.efternamn.getText().toString();
        String epost = this.epost.getText().toString();
        String lösenord = this.lösenord.getText().toString();
        String typ = kontotyp.getSelectedItem().toString();

        if(typ.contentEquals("Matchboxare")) {
            String ålderInnanParse = this.ålder.getText().toString();
            String viktInnanParse = this.vikt.getText().toString();
            String längdInnanParse = this.längd.getText().toString();
            age = Integer.parseInt(ålderInnanParse);
            weight = Integer.parseInt(viktInnanParse);
            lenght = Integer.parseInt(längdInnanParse);
        }

        Users user = null;
        if(typ.contentEquals("Matchboxare")){
            user = new Users(name,efternamn,epost,lösenord, stance.getSelectedItem().toString(),
                    age,weight,lenght, typ);
        }else {
            typ = "Coach";
            user = new Users(name,efternamn,epost,lösenord,typ);
        }

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.child(name).setValue(user);
    }

}
