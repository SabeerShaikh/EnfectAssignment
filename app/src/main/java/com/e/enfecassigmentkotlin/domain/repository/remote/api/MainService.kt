package com.enfecassignment.domain.repository.remote.api

import com.e.enfecassigmentkotlin.domain.model.PostData
import com.e.enfecassigmentkotlin.domain.model.UserData

import com.enfecassignment.domain.repository.remote.APIService
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import java.util.ArrayList


interface MainService : APIService {
    @Headers("Content-Type: application/json")
    @GET("posts")
    fun getPostsData(): Call<ArrayList<PostData>?>?

    @Headers("Content-Type: application/json")
    @GET("users")
    fun getUserData(): Call<ArrayList<UserData>?>?
}
