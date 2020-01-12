/*
package com.example.omartotangy.myapplication.Coach;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.omartotangy.myapplication.PublicClasses.Blankett;
import com.example.omartotangy.myapplication.R;

import java.util.Calendar;

*/
/**
 * A simple {@link Fragment} subclass.
 *//*

public class EventBlankett extends Fragment {
    private Spinner spinn;
    private EditText eventnamn, startTid, slutTid;
    private TextView datum;
    private Button buttonSpara, buttonAvbryt;
    private SkapaEventFragment dialogFragment;
    TimePickerDialog timePickerDialog;
    Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);

    public EventBlankett() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_event_blankett, container, false);

        visaDialogRutan();

        spinn = v.findViewById(R.id.spinner);
        eventnamn = v.findViewById(R.id.EventNamn);
        //String datume3 = getArguments().get("datum").toString();

        buttonAvbryt = v.findViewById(R.id.avbrytId);
        buttonAvbryt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vart ska vi hamna när vi klickar på avbryt. hem katalogen?
                // vart är hemkalatlogen??
                // ens nögvändigt och ha denna med tanke på att vi har tillbaka knappen.
                // vill man inte skapa en event kan man klicka sig tbx!!!
            }
        });

        slutTid = v.findViewById(R.id.slutTid);
        slutTid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        slutTid.setText(String.format("%02d:%02d" , hourOfDay , minute) );
                        slutTid.setText(hourOfDay + ":" + minute );
                    }
                }, hour , minute , false);

                timePickerDialog.show();
            }
        });
        startTid = v.findViewById(R.id.startTid);
        startTid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTid.setText(String.format("%02d:%02d" , hourOfDay , minute));
                        startTid.setText(hourOfDay + ":" + minute);
                    }
                }, hour , minute , false);

                timePickerDialog.show();
            }
        });
        buttonSpara = v.findViewById(R.id.buttonSpara);
        buttonSpara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spara();
            }
        });
        datum = v.findViewById(R.id.Datum);
        datum.setText(dialogFragment.hämtaDatum());
        //datum.setText(datume3);
        return v;
    }

    public void visaDialogRutan(){
        dialogFragment = new SkapaEventFragment();
        dialogFragment.show(getActivity().getSupportFragmentManager(), "calender");

    }
    public void spara(){
        //dialogFragment.
        String typ = spinn.getSelectedItem().toString();
        datum.setText(dialogFragment.hämtaDatum());
        String datum1 = datum.getText().toString();
        String eventNamn = eventnamn.getText().toString();
        String startTiden = startTid.getText().toString();
        String slutTiden = slutTid.getText().toString();
        Blankett blankett = new Blankett(typ,eventNamn,startTiden, slutTiden,datum1);
        Toast.makeText(getActivity() , "eventet har sparats", Toast.LENGTH_LONG).show();
        startTid.setText("");
        slutTid.setText("");
        eventnamn.setText("");
        //datum.setText("");
    }

}


*/


package com.example.omartotangy.myapplication.Coach;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.omartotangy.myapplication.PublicClasses.Blankett;
import com.example.omartotangy.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


/**
 * @author Kasim Mirzada
 * This class shows a form that can be filled out in order to create an event to the rest of the boxers.
 * Uses timepickerdialog and Calendar class to get the date.
 * A simple {@link Fragment} subclass.
 */
public class EventBlankett extends Fragment{
    private Spinner spinn;
    private EditText eventnamn, startTid, slutTid;
    private EditText datum;
    private Button buttonSpara, buttonAvbryt;
    TimePickerDialog timePickerDialog;
    Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    public EventBlankett() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_event_blankett, container, false);
        spinn = v.findViewById(R.id.spinner);
        eventnamn = v.findViewById(R.id.EventNamn);
        String datume3 = getArguments().get("datum").toString();

        datum = v.findViewById(R.id.Datum);
        datum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment dialogFragment = new SkapaEventFragment();
                dialogFragment.show(getActivity().getSupportFragmentManager() , "calender");
            }
        });
        buttonAvbryt = v.findViewById(R.id.avbrytId);
        buttonAvbryt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rensaBlankett();
            }
        });

        slutTid = v.findViewById(R.id.slutTid);
        slutTid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        slutTid.setText(String.format("%02d:%02d" , hourOfDay , minute) );
                        slutTid.setText(hourOfDay + ":" + minute );
                    }
                }, hour , minute , false);

                timePickerDialog.show();
            }
        });
        startTid = v.findViewById(R.id.startTid);
        startTid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startTid.setText(String.format("%02d:%02d" , hourOfDay , minute));
                        startTid.setText(hourOfDay + ":" + minute);
                    }
                }, hour , minute , false);

                timePickerDialog.show();
            }
        });
        buttonSpara = v.findViewById(R.id.buttonSpara);
        buttonSpara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spara();
            }
        });
        datum = v.findViewById(R.id.Datum);
        datum.setText(datume3);
        return v;
    }

    /**
     * This method saves the input on the form, send it to firebase and then clears the form.
     */
    public void spara(){
        String typ = spinn.getSelectedItem().toString();
        String datum1 = datum.getText().toString();
        String eventNamn = eventnamn.getText().toString();
        String startTiden = startTid.getText().toString();
        String slutTiden = slutTid.getText().toString();
        Blankett blankett = new Blankett(typ,eventNamn,startTiden, slutTiden,datum1);

        if(typ.length()>0 && datum1.length()>0 && eventNamn.length()>0&&startTiden.length()>0&&slutTiden.length()>0){
            sendToDatabase(blankett);
            Toast.makeText(getActivity() , "eventet har sparats", Toast.LENGTH_LONG).show();
            startTid.setText("");
            slutTid.setText("");
            eventnamn.setText("");
            datum.setText("");
        }else{
            Toast.makeText(getActivity(),"Fyll i hela blanketten", Toast.LENGTH_LONG).show();
        }


    }

    /**
     * This method clears the form when user clicks on "avbryt".
     */
    public void rensaBlankett(){
        startTid.setText("");
        slutTid.setText("");
        eventnamn.setText("");
        datum.setText("");

    }

    /**
     * This method sends the form to firebase.
     * @param blankett Blankett, a java class to store the form.
     */
    public void sendToDatabase(Blankett blankett){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("event");
        mDatabase.child(blankett.getTyp()).child(blankett.getNamn()).setValue(blankett);
    }
}
