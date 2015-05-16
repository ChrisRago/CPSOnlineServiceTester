package com.chrisrago.cpsonlineservicetester;

/**
 * Created by chris on 5/12/2015.
 */
public class ConnectionString {

    private long connectionId;
    private String alias;
    private String value;

    public ConnectionString(){}

    public long getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(long connectionId) {
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
