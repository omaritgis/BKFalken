package com.example.omartotangy.myapplication.Matchboxare;

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
import android.widget.TextView;

import com.example.omartotangy.myapplication.PublicClasses.Anslagstavla;
import com.example.omartotangy.myapplication.R;

/**
 * @author Omar Totangy
 * This class is a navigation drawer activity. From this activity can the user navigate to different activities and fragments.
 */

public class MatchboxareFragm extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, WorkoutFragment.openWorkoutsActivities {

    private String email, password;
    private DrawerLayout drawer;
    //private SendData send;
    private Bundle bundle;
    private TextView drawerName;
    private String namnEfternam, förnamn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchboxare_fragm);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email = getIntent().getStringExtra("användarnamn");
        password = getIntent().getStringExtra("lösenord");

        namnEfternam = getIntent().getStringExtra("namn");
        förnamn = getIntent().getStringExtra("förnamn");
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        drawerName = headerView.findViewById(R.id.navbarname);
        drawerName.setText(namnEfternam);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            createBundle();
            MatcherFragment matchFrag = new MatcherFragment();
            matchFrag.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, matchFrag).commit();
            navigationView.setCheckedItem(R.id.nav_matcher);
        }
    }

    /**
     * Sends needed userdata to next fragment to fetch correct user.
     */
    public void createBundle(){
        bundle = new Bundle();
        bundle.putString("epost", email);
    }

    @Override
    public void onBackPressed() {
        //drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.matchboxare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Settings.class);
            intent.putExtra("namn", förnamn);
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

        if (id == R.id.nav_matcher) {
            // Handle the camera action
            MatcherFragment matchFrag = new MatcherFragment();
            matchFrag.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, matchFrag).commit();
        } else if (id == R.id.nav_träningar) {
            WorkoutFragment workFrag = new WorkoutFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, workFrag).commit();
        } else if (id == R.id.nav_schema) {
            SchemaFragment schemaFragment = new SchemaFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, schemaFragment).commit();
        } else if (id == R.id.nav_tavla) {
            Anslagstavla anslagstavla = new Anslagstavla();
            Bundle bundle = new Bundle();
            bundle.putString("förnamn", förnamn);
            anslagstavla.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, anslagstavla).commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * This is one of three callback methods from the CreatedWorkouts class. This activity starts another activity when a button is clicked on the CreatedWorkouts fragment class.
     */
    @Override
    public void openImportedWorkouts() {
        Intent intent = new Intent(this, ImportedWorkouts.class);
        startActivity(intent);
    }

    @Override
    public void openCreatedWorkouts() {
        Intent intent = new Intent(this, CreatedWorkouts.class);
        startActivity(intent);
    }

    @Override
    public void openCreateWorkouts() {
        Intent intent = new Intent(this, NewWorkout.class);
        startActivity(intent);
    }

}
