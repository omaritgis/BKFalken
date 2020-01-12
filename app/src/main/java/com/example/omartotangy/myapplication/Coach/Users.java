package com.example.omartotangy.myapplication.Coach;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * @author Omar Totangy
 * This class gather user info. The class uses setters and getters for everything. It also has three constructors.
 * One constructor is empty, it is for the database, when it is read.
 * One does not take in wins, losses and draws, that is for the coach.
 * The third is for the boxer.
 */

@IgnoreExtraProperties
public class Users {

    public String name, lastname, email, password, stance, type;
    public int age, weight, lenght, wins, losses, draws;

    public Users(){

    }

    public Users(String name, String lastname, String email, String password, String type){
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public Users(String name, String lastname, String email, String password,String stance, int age, int weight, int lenght, String type){
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.stance = stance;
        this.age = age;
        this.weight = weight;
        this.lenght = lenght;
        this.type = type;
    }

    public void setWins(int wins){
        this.wins = wins;
    }

    public void setLosses(int losses){
        this.losses = losses;
    }

    public void setDraws(int draws){
        this.draws = draws;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getDraws() {
        return draws;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getStance() {
        return stance;
    }

    public String getType() {
        return type;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public int getLenght() {
        return lenght;
    }
}
