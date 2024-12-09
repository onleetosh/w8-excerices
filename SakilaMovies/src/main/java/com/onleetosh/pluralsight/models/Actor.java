package com.onleetosh.pluralsight.models;

public class Actor {

    int actorID;
    String firstname;
    String lastname;

    public Actor(int actorID, String firstname, String lastname) {
        this.actorID = actorID;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public int getActorID() {
        return actorID;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setActorID(int id) {
        this.actorID = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return actorID +
                " " + firstname +
                " " + lastname;
    }
}
