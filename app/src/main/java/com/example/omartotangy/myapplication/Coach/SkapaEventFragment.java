/*
package com.example.omartotangy.myapplication.Coach;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.omartotangy.myapplication.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

*/
/**
 *
 *//*

public class SkapaEventFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private String datum;
    private Boolean kanHämta = false;
    //private skickaDatum sd;
    private CalendarView cv;
    Calendar c = Calendar.getInstance();
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int minute = c.get(Calendar.MINUTE);
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_skapa_event, container, false);
        cv = v.findViewById(R.id.calederId);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Date d = new Date(year, month, day);
                String dag = d.toString();
                dag = dag.substring(0,10);
                skapaDialogRuta(dag);
            }
        });

        return v;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dp = new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        //DatePickerDialog dp2 = new DatePickerDialog(getActivity().getSupportFragmentManager(), year, month, day);

        return dp;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        skickaDatum(currentDateString);
        kanHämta = true;
    }

    public void skickaDatum(String currentDateString){
        datum = currentDateString;

    }

    public String hämtaDatum(){
        if(kanHämta){
            return datum;
        }else {
            return "Välj ett datum";
        }

    }

    public void skapaDialogRuta(String d){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());

        View v = getLayoutInflater().inflate(R.layout.fragment_event_blankett, null);
        Spinner spinn = v.findViewById(R.id.spinner);
        EditText eventnamn = v.findViewById(R.id.EventNamn);
        //String datume3 = getArguments().get("datum").toString();

        Button buttonAvbryt = v.findViewById(R.id.avbrytId);
        buttonAvbryt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // vart ska vi hamna när vi klickar på avbryt. hem katalogen?
                // vart är hemkalatlogen??
                // ens nögvändigt och ha denna med tanke på att vi har tillbaka knappen.
                // vill man inte skapa en event kan man klicka sig tbx!!!
            }
        });

        EditText slutTid = v.findViewById(R.id.slutTid);
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
        EditText startTid = v.findViewById(R.id.startTid);
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
        Button buttonSpara = v.findViewById(R.id.buttonSpara);
        buttonSpara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //spara();
            }
        });
        TextView datum = v.findViewById(R.id.Datum);
        datum.setText(d);
        mBuilder.setView(v);
        AlertDialog box = mBuilder.create();
        box.show();
    }
}
*/
package com.example.omartotangy.myapplication.Coach;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.omartotangy.myapplication.R;
import java.util.Calendar;

/**
 * @author Kasim Mirzada
 * detta är en klass som visar kalendern i en dialogruta med dagens datum
 *
 */
public class SkapaEventFragment extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_skapa_event, container, false);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity() , year, month, day );
    }
}