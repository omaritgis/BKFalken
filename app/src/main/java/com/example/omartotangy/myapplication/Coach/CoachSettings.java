package com.example.omartotangy.myapplication.Coach;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.omartotangy.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CoachSettings extends AppCompatActivity {

    private EditText email, password, passwordConfirm;
    private String name;
    private Users thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_settings);

        name = getIntent().getStringExtra("namn");

        email = findViewById(R.id.epost);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.passwordConfirm);

        getDeafaultSettings();
        Button spara = findViewById(R.id.spara);
        spara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDatabase();
            }
        });
    }

    /**
     * This method fetches data about the user from firebase.
     */
    public void getDeafaultSettings() {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users post = null;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    post = postSnapshot.getValue(Users.class);
                    if (post.getName().contentEquals(name) && post.getType().contentEquals("Coach")) {
                        thisUser = post;
                        email.setText(post.getEmail());
                        password.setText(post.getPassword());
                        passwordConfirm.setText(post.password);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    /**
     * This method updates the database for the Coach.
     */
    public void updateDatabase(){
        DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("users");
        String epost = email.getText().toString();
        String lösenord = password.getText().toString();
        String lösenordRep = passwordConfirm.getText().toString();

        if(!lösenord.contentEquals(lösenordRep)){
            Toast.makeText(this,"Lösenordet matchar inte bekräftade lösenord", Toast.LENGTH_LONG).show();
        }else{
            thisUser.email = epost;
            thisUser.password = lösenord;
            dRef.child(name).setValue(thisUser);
        }
    }
}
