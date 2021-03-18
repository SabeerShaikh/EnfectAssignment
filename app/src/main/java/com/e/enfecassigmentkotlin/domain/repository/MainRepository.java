package com.e.enfecassigmentkotlin.domain.repository;

import androidx.lifecycle.MutableLiveData;

import com.e.enfecassigmentkotlin.domain.RepositoryResponse;
import com.e.enfecassigmentkotlin.domain.model.PostData;
import com.e.enfecassigmentkotlin.domain.model.UserData;

import java.util.ArrayList;
import java.util.List;

public interface MainRepository {
    MutableLiveData<RepositoryResponse<ArrayList<PostData>>> getPostsData();

    MutableLiveData<RepositoryResponse<ArrayList<UserData>>> getUserData();

}
