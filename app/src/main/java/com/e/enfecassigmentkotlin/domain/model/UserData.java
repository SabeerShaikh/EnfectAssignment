package com.e.enfecassigmentkotlin.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserData implements Serializable {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;

    @SerializedName("username")
    public String username;
    @SerializedName("email")
    public String email;
    @SerializedName("company")
    public Company company;
}
