package com.example.omartotangy.myapplication.PublicClasses;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
 * This class loads messages stored in firebase, it can also upload messages to the database.
 *
 * A simple {@link Fragment} subclass.
 */
public class Anslagstavla extends Fragment {
    private EditText input;
    private Button upload;
    private ListView lv;
    private DatabaseReference dRef;
    private ArrayList<Chat> meddelande = new ArrayList<>();
    private ArrayList<String> entries = new ArrayList<>();
    private ArrayAdapter<String> fullEntries;
    private String namn;
    private int i = 0;
    private int messageNumber;
    private boolean canI;

    public Anslagstavla() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_anslagstavla, container, false);
        input = v.findViewById(R.id.input);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            namn = bundle.getString("förnamn");
        }

        upload = v.findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
        lv = v.findViewById(R.id.list);

        connectToDatabase2();
        return v;
    }

    public void connectToDatabase2(){
        dRef = FirebaseDatabase.getInstance().getReference("uploads");

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Chat post = null;
                int i = 0;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String s = postSnapshot.getKey();
                    String first = s.replaceAll("[0-9]", "");
                    String str = s.replaceAll("\\D+", "");
                    post = postSnapshot.getValue(Chat.class);

                    if(first.contentEquals(namn)){
                        messageNumber = Integer.parseInt(str);
                        messageNumber++;
                        if(messageNumber >9){
                            messageNumber = 0;
                        }
                    }
                    meddelande.add(new Chat(post.getUploader(), post.getMessage()));
                    entries.add(meddelande.get(i).getUploader() + "\n" + meddelande.get(i).getMessage());
                    i++;
                }
                drawList();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Fel grejer", Toast.LENGTH_LONG).show();
            }
        };

        dRef.addValueEventListener(postListener);
    }

    public void connectToDatabase(){
        dRef = FirebaseDatabase.getInstance().getReference("uploads");

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post = null;
                ArrayList<String> names = new ArrayList<>();
                ArrayList<String > messages = new ArrayList<>();
                int i = 0;
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String s = postSnapshot.getKey();
                    post = postSnapshot.getValue(Post.class);

                    names.add(s);

                    if(s.contentEquals(namn)){
                        for(DataSnapshot snapshot : postSnapshot.getChildren()){
                            String uploadNumber = snapshot.getKey();
                            //int uploadNumberInt = Integer.parseInt(uploadNumber);
                            Post post1 = snapshot.getValue(Post.class);
                            //messages.add(post1.)
                        }
                    }

                    //meddelande.add(new Chat(names.get(i), post.getAllPosts()));
                    entries.add(meddelande.get(i).getUploader() + "\n" + meddelande.get(i).getMessage());
                    i++;
                }
                drawList();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Fel grejer", Toast.LENGTH_LONG).show();
            }
        };

        dRef.addValueEventListener(postListener);
    }

    /**
     * Publish message to the firebase database.
     *
     */
    public void sendData2(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("uploads");
        Chat message = new Chat(namn, input.getText().toString());

        HashMap<String,String> messageNumberAndMessage = new HashMap<>();
        messageNumberAndMessage.put(""+messageNumber,input.getText().toString());


        Post post = new Post(messageNumberAndMessage);
        post.publish(namn);
        /*if(canI){
            message.setNumberOfMessages(messageNumber + 1);
        }else{
            message.setNumberOfMessages(0);
        }*/




        String namn = message.getUploader();
        //mDatabase.child(namn).setValue(message);
        //mDatabase.child(namn).child(""+messageNumber).setValue(message);
        mDatabase.child(namn).setValue(post);
    }

    public void sendData(){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("uploads");
        Chat message = new Chat(namn, input.getText().toString());

        String namn = message.getUploader();
        mDatabase.child(namn+messageNumber).setValue(message);
        //mDatabase.child(namn).child(""+messageNumber).setValue(message);
        //mDatabase.child(namn).setValue(post);
    }

    /**
     * Creates the listview containing a custom layout, id of the textview and what is entered
     * Uses an arrayadapter to achieve the results
     */
    public void drawList(){

        try{
            fullEntries = new ArrayAdapter<>(getActivity(), R.layout.message_uploads, R.id.uploader, entries); // Kan skapa nullpointerexception vid vissa tillfällen
            lv.setAdapter(fullEntries);
            meddelande = new ArrayList<>();
            entries = new ArrayList<>(); //Gör kaos i koden någonstans
        }catch (NullPointerException npe){

        }

    }

}
