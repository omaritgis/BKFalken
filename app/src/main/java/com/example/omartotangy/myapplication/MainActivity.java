package com.example.omartotangy.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omartotangy.myapplication.Coach.CoachMainScreen;
import com.example.omartotangy.myapplication.Coach.Users;
import com.example.omartotangy.myapplication.Matchboxare.MatchboxareFragm;
import com.example.omartotangy.myapplication.PublicClasses.ResetPassword;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * @author Omar Totangy
 * <br>
 *     This class is the first activity shown when the app is opened. Purpose is to logg in to use the app.
 */


public class MainActivity extends AppCompatActivity {

    private Button loggaIn, reset;
    private TextView username, password;
    private DatabaseReference userRef;
    private boolean okej;
    private ArrayList<Users> usersList = new ArrayList<Users>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        loggaIn = findViewById(R.id.loggin);
        connectDatabase();
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
        loggaIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAccount();
            }
        });


    }

    /**
     * Creates a AlertDialog that shows info about the app.
     * @param view From XML the onClick option can be set to this method.
     */
    public void omApppen(View view){
        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Om Appen");
        builder.setMessage("Denna app är skapad av Omar Totangy. Via denna app kan man på ett smidigt sätt hålla koll på vad som händer i BK Falken, man kan bland annat hålla koll på vilka event som finns, ta del av olika övningar som tränare lägger upp samt att kunna ta del av en gruppchatt. ");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    /**
     * This method connects to firebase to fetch user data.
     */
    public void connectDatabase(){
        userRef = FirebaseDatabase.getInstance().getReference("users");
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users post = null;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    post = postSnapshot.getValue(Users.class);
                    usersList.add(post);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Fel grejer", Toast.LENGTH_LONG).show();
            }
        };

        userRef.addListenerForSingleValueEvent(postListener);
    }

    /**
     * Checks if the username and password matches a firebase stored user.
     */
    public void checkAccount(){
        String email = username.getText().toString();
        String pass = password.getText().toString();

        Users thisUser = null;
        //The search: lets us break the search if the if-statement is met, this allows us to avoid going through the whole list if the user is already found
        // If userList.size == 0 Toast = Kan ej ansluta till internet
        search:
        {
            for (Users u : usersList) {

                if (email.contentEquals(u.getEmail()) && pass.contentEquals(u.getPassword())) {
                    thisUser = u;
                    okej = true;
                    loggin(thisUser);
                    break search;
                }
            }
        }
    }

    /**
     * Starts activity based on what type the account is, Matchboxare or Coach have different activities.
     * @param u The fetched user from the checkAccount method
     */
    public void loggin(Users u){


        if(u.getType().contentEquals("Matchboxare") && okej){
            Intent intent = new Intent(this, MatchboxareFragm.class);
            intent.putExtra("användarnamn", u.getEmail());
            intent.putExtra("lösenord", u.getPassword());
            intent.putExtra("namn", u.getName() + " " + u.getLastname());
            intent.putExtra("förnamn", u.getName());
            startActivity(intent);
        }else if(u.getType().contentEquals("Coach") && okej){
            Intent intent = new Intent(this, CoachMainScreen.class);
            intent.putExtra("användarnamn", u.getEmail());
            intent.putExtra("lösenord", u.getPassword());
            intent.putExtra("förnamn", u.getName());
            startActivity(intent);
        }else{
            Toast.makeText(this,"Fel användarnamn eller lösenord", Toast.LENGTH_LONG).show();
        }
    }

    public void resetPassword(){
        Intent intent = new Intent(this, ResetPassword.class);
        startActivity(intent);
    }
}
