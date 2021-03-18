package com.e.enfecassigmentkotlin.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostData implements Serializable {
    @SerializedName("userId")
    public String userId;
    @SerializedName("id")
    public String id;
    @SerializedName("title")
    public String title;
    @SerializedName("body")
    public String body;
}

