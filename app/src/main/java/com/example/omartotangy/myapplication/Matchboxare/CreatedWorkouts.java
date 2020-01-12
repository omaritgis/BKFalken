package com.example.omartotangy.myapplication.Matchboxare;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omartotangy.myapplication.PublicClasses.ExpandableListViewAdapter;
import com.example.omartotangy.myapplication.R;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Omar Totangy
 * This class shows the created workouts of a boxer. The workouts is stored in a textfile locally on the device. It uses a expanble list view to show the results.
 *
 */

public class CreatedWorkouts extends AppCompatActivity {

    private String repre;
    private LinearLayout lay, child, parent;
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<String> workoutName = new ArrayList<>();
    private ExpandableListView elv;
    private ExpandableListViewAdapter elvAdapter;
    private ArrayList<String> listan = new ArrayList<>();
    private int i = 0;
    private final String FILE_NAME = "createdWorkouts.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_created_workouts);

        lay = findViewById(R.id.myShiet);
        elv = findViewById(R.id.expandables);

        try{
            repre = getIntent().getStringExtra("nyckel2");
            //Toast.makeText(getApplicationContext(), repre, Toast.LENGTH_LONG).show();
            seperate();
        }catch (NullPointerException npe){
            // När någon klickar på knappen "mina träningspass" utan att ha skapat ett träningspass
            try{
                loadFile();
            }catch (NullPointerException npe3){
                npe3.printStackTrace();
            }
        }

    }

    /**
     * @deprecated Method is not needed. Use loadfile() instead.
     */
    public void seperate(){

        ArrayList<String> delare = new ArrayList<>();
        String[] delar = repre.split(",");

        for(int i = 0; i<delar.length; i++){
            delare.add(delar[i]);
        }

        setLay(delare);

    }

    /**
     * @deprecated Use prepareInfo() instead
     * @param delare
     */
    public void setLay(ArrayList<String> delare) {

        AppCompatTextView[] grejer = new AppCompatTextView[delare.size()];

        for(int i = 0; i<delare.size(); i++){
            grejer[i] = new AppCompatTextView(this);
            grejer[i].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            grejer[i].setId(i);
            grejer[i].setText(delare.get(i));
        }



        AppCompatTextView namnet = new AppCompatTextView(getApplicationContext());

        namnet.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        for(int i = 0; i<delare.size(); i++){
            grejer[i].setTextSize(20);
            lay.addView(grejer[i]);
            Toast.makeText(getApplicationContext(), grejer[i].getText(), Toast.LENGTH_LONG).show();
        }

    }

    /**
     * This method creates the expandable listview. It needs an arraylist.
     * @param listan String arraylist
     */
    public void prepareInfo(ArrayList<String> listan){
        String namnet = "";
        ArrayList<String> nameOfWorkouts = new ArrayList<>(); // Ska bli parentItem i expandableListView
        boolean addChildrenItems = false; // Separerar child och parent
        ArrayList<String> childrenItems = null; // Ska bli underrubriker i listan
        HashMap<String, ArrayList<String>> childList = new HashMap<>();


        for (int i = 0; i<listan.size();i++){
            if(listan.get(i).contentEquals("[")){
                namnet = listan.get(i - 1);
                nameOfWorkouts.add(listan.get(i - 1)); // Parent name lista
                addChildrenItems = true;
            }
            if(addChildrenItems == true && !listan.get(i).contentEquals("[")){
                if( childrenItems == null)
                    childrenItems = new ArrayList<>();
                if(listan.get(i).contentEquals("]")){
                    addChildrenItems = false;
                    //childList.put(nameOfWorkouts.get(j), childrenItems);
                    //ExpandableListViewAdapter elvAdapter = new ExpandableListViewAdapter(this, childList, nameOfWorkouts);
                    //elv.setAdapter(elvAdapter);


                    //setExpandableListView(childrenItems, namnet);
                    childList.put(namnet, childrenItems);
                    childrenItems = null;
                }else{
                    childrenItems.add(listan.get(i));
                }
            }
        }
        elvAdapter = new ExpandableListViewAdapter(this, childList, nameOfWorkouts);
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


    /**
     * This method starts the RondTimer activity.
     * @param groupposition position of the expandble listview that was longclicked.
     */
    public void startWorkout(int groupposition){
        Intent intent = new Intent(this, RondTimer.class);
        int i = 0;
        for(String s : listan){
            intent.putExtra("träning" + i, s);
            i++;
        }


        startActivity(intent);
    }

    /**
     * This method reads from the file "createdWorkouts.txt" and adds data to an arraylist.
     */
    public void loadFile(){

        try{
            InputStream is = openFileInput("createdWorkouts.txt");

            if(is != null){
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String reciever = "";
                StringBuilder sb = new StringBuilder();

                while((reciever = br.readLine()) != null){
                    data.add(reciever);

                }
                prepareInfo(data);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void modernDialog(int groupPosition){
        Dialog modern = new Dialog(this);
        modern.setContentView(R.layout.workout_options_box);
        String title = elv.getExpandableListAdapter().getGroup(groupPosition).toString();
        Button starta = modern.findViewById(R.id.startWorkout);
        Button radera = modern.findViewById(R.id.deleteWorkout);
        TextView header = modern.findViewById(R.id.excercize);
        header.setText(title);
        radera.setEnabled(true);// Detta får fixas senare

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
                deleteWorkout(title);
            }
        });

        modern.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        modern.show();

        /*mBuilder.setView(view);
        AlertDialog box = mBuilder.create();
        box.show();*/

    }

    public void deleteWorkout(String title){
        try {
            Boolean delete = false;
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(FILE_NAME, MODE_PRIVATE));
            // Append betyder bifoga. Om filen skapas för första gången kan det ske problem
            // Inte bara append som skapar problemet, utan även ändringarna i newTräningsPass

            for(String s : data){
                if(s.contentEquals(title) || delete){
                    delete = true;
                    if(s.contentEquals("]")){
                        delete = false;
                    }
                }
                if(!s.contentEquals(title) && !delete){
                    osw.write(s + "\n");
                }

            }
            osw.close();
            prepareInfo(data);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
