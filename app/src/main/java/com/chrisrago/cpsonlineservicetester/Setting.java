package com.chrisrago.cpsonlineservicetester;

/**
 * Created by chris on 5/12/2015.
 */
public class Setting {

    private int optionId;
    private String optionName;
    private String optionValue;

    public Setting(){}

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
}
