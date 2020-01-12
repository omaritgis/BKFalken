package com.example.omartotangy.myapplication.Matchboxare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.omartotangy.myapplication.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * @author Omar Totangy
 * This class allows the user to create a new workout that is saved locally on the unit.
 */
public class NewWorkout extends AppCompatActivity {

    private NewTräningsPass workout = new NewTräningsPass();
    private Spinner spinn;
    private EditText antal;
    private String val;
    private ListView list;
    private ArrayList<String> entries = new ArrayList<>();
    private ArrayAdapter<String> fullEntries;
    private int rounds = 0;
    private final String FILE_NAME = "createdWorkouts.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        spinn = findViewById(R.id.choice);
        list = findViewById(R.id.listview);
        antal = findViewById(R.id.antal);
        spinn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(adapterView.getSelectedItemPosition() == 0 || adapterView.getSelectedItemPosition() ==
                        1 || adapterView.getSelectedItemPosition() == 6){
                    antal.setHint(R.string.ronder);
                }else if(adapterView.getSelectedItemPosition() == 2 || adapterView.getSelectedItemPosition() == 3){
                    antal.setHint(R.string.antal);
                }else if(adapterView.getSelectedItemPosition() == 4){
                    antal.setHint(R.string.antalMin);
                }else{
                    antal.setHint(R.string.antalKM);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * When user presses "add" button this method is used, adds string to a listview which programmatically grows as more items are added.
     * @param view View
     */
    public void addWorkout(View view) {
        String text = antal.getText().toString();
        String text4 = antal.getHint().toString();
        String text2 = spinn.getSelectedItem().toString();

        if (text.contentEquals("")) {
            Toast.makeText(this, "Fyll i hur många ronder!", Toast.LENGTH_LONG).show();
        } else {
            rounds = Integer.parseInt(text);
            //String text3 = text2 + " " + text + " " + text4;
            String text3 = text2 + " " + text;
            entries.add(text3);
            fullEntries = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, entries);
            list.setAdapter(fullEntries);
            workout.addWorkouts(text3);
        }
    }

    /**
     * When user presses the save button they have to have named the workout something. Needs string in order to work.
     * @param view View
     */
    public void setName(View view){

            EditText namn = findViewById(R.id.NamnTxt);
            String name = namn.getText().toString();
            if(name.contentEquals("")){
                Toast.makeText(getApplicationContext(), "Du måste skriva in ett namn", Toast.LENGTH_LONG).show();
            }else{
                workout.setNamn(name);
                save();
            }
    }

    /**
     * This method saves created workouts locally using an outputstreamwriter, file is in appen mode allowing it to add data.
     */
    public void save(){
        //String representationen = workout.getStringRepresentation();

        try {
            OutputStreamWriter osw = new OutputStreamWriter(openFileOutput(FILE_NAME, MODE_APPEND));
            osw.append(workout.getNamn() + "\n");
            // Append betyder bifoga. Om filen skapas för första gången kan det ske problem
            // Inte bara append som skapar problemet, utan även ändringarna i newTräningsPass

            for(String s : workout.getListan()){
                osw.append(s + "\n");
            }
            osw.close();
            Toast.makeText(getApplicationContext(), "saved to" + getFilesDir(), Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
