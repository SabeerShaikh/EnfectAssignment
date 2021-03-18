package com.e.enfecassigmentkotlin.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Company implements Serializable {
    @SerializedName("name")
    public String name;
    @SerializedName("catchPhrase")
    public String catchPhrase;
    @SerializedName("bs")
    public String bs;

}
