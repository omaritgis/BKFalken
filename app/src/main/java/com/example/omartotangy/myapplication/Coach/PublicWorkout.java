package com.example.omartotangy.myapplication.Coach;

import java.util.ArrayList;

/**
 * @author Omar Totangy
 * This class is to store public saved workouts. There is the name of the workout and an arraylist with the exercises themselves.
 * Uses setters and getters, except of the arraylist, the list can only be set, but since it is public, there has been no need for a getter.
 */
public class PublicWorkout extends ArrayList {
    public ArrayList<String> pass = new ArrayList<>();
    private String name;

    public void setPas(String s){
        pass.add(s);
    }
    public void setName(String s){
        name = s;
    }
    public String getName(){
        return name;
    }
}
