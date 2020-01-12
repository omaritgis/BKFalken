package com.example.omartotangy.myapplication.Coach;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.omartotangy.myapplication.PublicClasses.Anslagstavla;
import com.example.omartotangy.myapplication.R;
import com.example.omartotangy.myapplication.Matchboxare.Settings;
import com.google.firebase.database.DatabaseReference;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Omar Totangy, kasim mirzada
 * This class is a navigation drawer activity. From this activity can the user navigate to different activities and fragments.
 */


public class CoachMainScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private DatabaseReference userRef;
    private ArrayList<Users> usersList = new ArrayList<Users>();
    private String email, password, förnamn;
    private Users thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_main_screen);

        email = getIntent().getStringExtra("användarnamn");
        password = getIntent().getStringExtra("lösenord");
        förnamn = getIntent().getStringExtra("förnamn");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //connectDatabase();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        TextView drawerName = headerView.findViewById(R.id.navbarname);
        drawerName.setText(förnamn);

        if(savedInstanceState == null){
            android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, new CoachBoxare()).commit();
            navigationView.setCheckedItem(R.id.nav_camera);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.coach_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, CoachSettings.class);
            intent.putExtra("namn",förnamn);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            CoachBoxare cb = new CoachBoxare();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, cb).commit();
        } else if (id == R.id.nav_gallery) {
            SharedWorkout cb = new SharedWorkout();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, cb).commit();
        } else if (id == R.id.nav_slideshow) {
            EventBlankett ebe = new EventBlankett();
            SkapaEventFragment eb = new SkapaEventFragment();
            eb.show(getSupportFragmentManager(), "haha" );
        } else if (id == R.id.nav_manage) {
            RegisterUser registerUser = new RegisterUser();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, registerUser).commit();
        } else if (id == R.id.nav_share) {
            Anslagstavla anslagstavla = new Anslagstavla();
            Bundle bundle = new Bundle();
            bundle.putString("förnamn", förnamn);
            anslagstavla.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, anslagstavla).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * This method gets the date when it is set on the calendar.
     * @param datePicker DatePicker
     * @param year int year
     * @param month int month
     * @param dayOfMonth int day of the month
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        visaDatum(currentDateString);
    }

    /**
     * This method opens the fragment, "eventBlankett" and sends the date in a bundle.
     * @param s date in string format
     */
    public void visaDatum(String s){
        Bundle bundle = new Bundle();
        bundle.putString("datum", s);
        EventBlankett eb = new EventBlankett();
        eb.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, eb).commit();
    }

}


