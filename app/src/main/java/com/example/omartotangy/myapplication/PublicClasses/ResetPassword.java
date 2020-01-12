package com.example.omartotangy.myapplication.PublicClasses;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omartotangy.myapplication.Coach.Users;
import com.example.omartotangy.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResetPassword extends AppCompatActivity {

    private TextView email;
    private Button submit;
    private DatabaseReference dRef;
    private Users thisUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = findViewById(R.id.email);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectToDatabase();
            }
        });


    }

    private void connectToDatabase(){
        dRef = FirebaseDatabase.getInstance().getReference("users");

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users post = null;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    post = postSnapshot.getValue(Users.class);
                    if(post.getEmail().contentEquals(email.getText().toString())){
                        thisUser = post;
                        resetPassword();
                    }
                }

                if(thisUser == null){
                    Toast.makeText(getApplication(),"Fel epost adress inmatad.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(getApplicationContext(), "Fel grejer", Toast.LENGTH_LONG).show();
            }
        };

        dRef.addListenerForSingleValueEvent(postListener);
    }

    public void resetPassword(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("lostPasswords");
        mDatabase.child(thisUser.email).setValue(thisUser.getPassword());
        Toast.makeText(this,"Ditt lösenord kommer att skickas inom 2-3 dagar.", Toast.LENGTH_LONG).show();
    }
}
