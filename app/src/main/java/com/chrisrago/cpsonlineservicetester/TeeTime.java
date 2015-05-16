package com.chrisrago.cpsonlineservicetester;

/**
 * Created by chris on 5/12/2015.
 */
public class TeeTime {

    private int teeTimeId;
    private String startTime;
    private String slotsAvailable;

    public TeeTime(){}

    public int getTeeTimeId() {
        return teeTimeId;
    }

    public void setTeeTimeId(int teeTimeId) {
        this.teeTimeId = teeTimeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSlotsAvailable() {
        return slotsAvailable;
    }

    public void setSlotsAvailable(String slotsAvailable) {
        this.slotsAvailable = slotsAvailable;
    }
}
