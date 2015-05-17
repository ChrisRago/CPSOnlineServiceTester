package com.chrisrago.cpsonlineservicetester;

/**
 * Created by chris on 5/12/2015.
 */
public class Reservation {

    private long id;
    private String confirmation;

    public Reservation(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public  void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }
}
