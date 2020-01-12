package com.example.omartotangy.myapplication.Matchboxare;

import java.util.ArrayList;

/**
 * @author Omar Totangy
 * This class stores the data about the workout created.
 */
public class NewTräningsPass {
    private int repRonder, skuggRonder, pushUpsRonder, plankanMin, joggKm, situpsAntal, sparrRonder;
    private String namn;
    private ArrayList<String> listan = new ArrayList<>();


    public void addWorkouts(String s){
        if(listan.size()<1){
            listan.add("[");
        }
        listan.add(s);
    }

    public ArrayList<String> getListan(){
        return listan;
    }

    public void setNamn(String namn) {
        this.namn = namn;
        String closeList = "]";
        listan.add(closeList);
        //listan.add(namn);
    }

    public String getNamn(){
        return namn;
    }

    public String getStringRepresentation() {
        /*
        ordning =
        Hopprep
        skuggboxning
        armhävningar
        situps
        plankan
        löpning
        sparring
        namn
         */



        String represent = "";

        for (String s: listan) {
            if(s.contentEquals(namn)){
                represent += s;
            }else {
                represent += s + ",";
            }
        }


        return represent;
    }

}
