package com.example.omartotangy.myapplication.PublicClasses;

/**
 * @author Kasim Mirzada
 * This class stores all data about a created event. It uses setters and getters in the class.
 *
 */

public class Blankett {
        private String typ, namn, startTid, slutTid, datum;

        public Blankett(){

        }
        public Blankett(String typ, String namn,String startTid,String slutTid, String datum){
            this.typ = typ;
            this.namn = namn;
            this.startTid = startTid;
            this.slutTid = slutTid;
            this.datum = datum;

        }

        public String getTyp() {
            return typ;
        }

        public void setTyp(String typ) {
            this.typ = typ;
        }

        public String getNamn() {
            return namn;
        }

        public void setNamn(String namn) {
            this.namn = namn;
        }

        public String getStartTid() {
            return startTid;
        }

        public void setStartTid(String startTid) {
            this.startTid = startTid;
        }

        public String getSlutTid() {
            return slutTid;
        }

        public void setSlutTid(String slutTid) {
            this.slutTid = slutTid;
        }

        public String getDatum() {
            return datum;
        }

        public void setDatum(String datum) {
            this.datum = datum;
        }
    }


