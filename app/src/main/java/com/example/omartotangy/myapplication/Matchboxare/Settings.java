package com.example.omartotangy.myapplication.Matchboxare;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.omartotangy.myapplication.Coach.Users;
import com.example.omartotangy.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings extends AppCompatActivity {
    private String stance, namn, efternamn;
    private int wins, losses, draws;
    private Spinner spinn;
    private Button save;
    private EditText weight, name, age, lenght, email, password, passwordConfirm;
    private Users thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        namn = getIntent().getStringExtra("namn");

        spinn = findViewById(R.id.spinner);
        passwordConfirm = findViewById(R.id.repeatPassword);
        weight = findViewById(R.id.weight);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        lenght = findViewById(R.id.lenght);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        save = findViewById(R.id.save);

        getDeafaultSettings();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDatabase();
            }
        });
    }

    /**
     * This method uppdates user info.
     */
    public void updateDatabase(){
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("users");
        Users user;

        stance = spinn.getSelectedItem().toString();
        String vikt = weight.getText().toString();
        String längd = lenght.getText().toString();
        String ålder = age.getText().toString();

        int vikt2 = Integer.parseInt(vikt);
        int längd2 = Integer.parseInt(längd);
        int ålder2 = Integer.parseInt(ålder);
        String epost = email.getText().toString();
        String lösenord = password.getText().toString();
        String lösenordRep = passwordConfirm.getText().toString();

        if(!lösenord.contentEquals(lösenordRep)){
            Toast.makeText(this,"Lösenordet matchar inte bekräftade lösenord", Toast.LENGTH_LONG).show();
            }else{
            user = new Users(namn, efternamn,epost,lösenord, stance,ålder2,vikt2,längd2,"Matchboxare");
            user.setWins(wins);
            user.setLosses(losses);
            user.setDraws(draws);
            dRef.child(namn).setValue(user);
        }
    }

    /**
     * This method fethes data about the user and displays it in the fields.
     */
    public void getDeafaultSettings(){


        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users post = null;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    post = postSnapshot.getValue(Users.class);
                    if(post.getName().contentEquals(namn)&&post.getType().contentEquals("Matchboxare")){
                        thisUser = post;
                        name.setText(post.getName());
                        name.setFocusable(false);
                        name.setFocusableInTouchMode(false);
                        name.setClickable(false);
                        efternamn = post.getLastname();
                        weight.setText(""+post.getWeight());
                        lenght.setText(""+post.getLenght());
                        age.setText(""+post.getAge());
                        email.setText(post.getEmail());
                        password.setText(post.getPassword());
                        passwordConfirm.setText(post.password);
                        wins = post.getWins();
                        losses = post.getLosses();
                        draws = post.getDraws();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Fel grejer", Toast.LENGTH_LONG).show();
            }
        };

        userRef.addListenerForSingleValueEvent(postListener);
    }
}
