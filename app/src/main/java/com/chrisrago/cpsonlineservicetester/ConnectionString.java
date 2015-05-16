package com.chrisrago.cpsonlineservicetester;

/**
 * Created by chris on 5/12/2015.
 */
public class ConnectionString {

    private int connectionId;
    private String alias;
    private String value;

    public ConnectionString(){}

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
