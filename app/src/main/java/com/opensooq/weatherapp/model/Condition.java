package com.opensooq.weatherapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Omar AlTamimi on 9/1/2016.
 */
public class Condition implements Serializable{
    @SerializedName("text")
    private String conditionText;
    @SerializedName("icon")
    private String iconLink;

    public String getConditionText() {
        return conditionText;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }
}
