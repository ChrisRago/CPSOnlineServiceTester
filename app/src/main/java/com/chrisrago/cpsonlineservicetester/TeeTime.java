package com.chrisrago.cpsonlineservicetester;

/**
 * Created by chris on 5/12/2015.
 */
public class TeeTime {

    private long teeTimeId;
    private String cpsTeeTimeId;
    private String startTime;
    private String slotsAvailable;

    public TeeTime(){}

    public long getTeeTimeId() {
        return teeTimeId;
    }

    public void setTeeTimeId(long teeTimeId) {
        this.teeTimeId = teeTimeId;
    }

    public String getCPSTeeTimeId() {
        return cpsTeeTimeId;
    }

    public void setCPSTeeTimeId(String cpsTeeTimeId) {
        this.cpsTeeTimeId = cpsTeeTimeId;
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
