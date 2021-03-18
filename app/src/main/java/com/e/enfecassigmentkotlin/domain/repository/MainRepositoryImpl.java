package com.e.enfecassigmentkotlin.domain.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.e.enfecassigmentkotlin.domain.RepositoryResponse;
import com.e.enfecassigmentkotlin.domain.model.PostData;
import com.e.enfecassigmentkotlin.domain.model.UserData;
import com.enfecassignment.domain.repository.remote.api.MainService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainRepositoryImpl implements MainRepository {

    private MainService service;

    public MainRepositoryImpl(MainService fileService) {
        this.service = fileService;
    }

    @Override
    public MutableLiveData<RepositoryResponse<ArrayList<PostData>>> getPostsData() {
        MutableLiveData<RepositoryResponse<ArrayList<PostData>>> result = new MutableLiveData<>();

        service.getPostsData().enqueue(new Callback<ArrayList<PostData>>() {

            @Override
            public void onResponse(Call<ArrayList<PostData>> call, Response<ArrayList<PostData>> response) {
                Log.d("DataDailyForcast111", "" + response.body().size());
                if (response.isSuccessful() && response.body() != null) {
                    result.setValue(new RepositoryResponse<>(response.body()));

                }
            }

            @Override
            public void onFailure(Call<ArrayList<PostData>> call, Throwable t) {
                Log.d("DataDailyForcast", "" + t.getMessage());
                Log.d("DataDailyForcast111", "" + call.request().url());
                result.setValue(new RepositoryResponse<ArrayList<PostData>>(t.getMessage(),
                        400));

            }

        });
        return result;
    }

    @Override
    public MutableLiveData<RepositoryResponse<ArrayList<UserData>>> getUserData() {
        MutableLiveData<RepositoryResponse<ArrayList<UserData>>> result = new MutableLiveData<>();

        service.getUserData().enqueue(new Callback<ArrayList<UserData>>() {

            @Override
            public void onResponse(Call<ArrayList<UserData>> call, Response<ArrayList<UserData>> response) {
                Log.d("DataDailyForcast111", "" + response.body().size());
                if (response.isSuccessful() && response.body() != null) {
                    result.setValue(new RepositoryResponse<>(response.body()));

                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserData>> call, Throwable t) {
                Log.d("DataDailyForcast", "" + t.getMessage());
                Log.d("DataDailyForcast111", "" + call.request().url());
                result.setValue(new RepositoryResponse<ArrayList<UserData>>(t.getMessage(),
                        400));

            }

        });
        return result;
    }

    /*@Override
    public MutableLiveData<RepositoryResponse<User>> getAllWeatherForecast(String mobileNum) {
        MutableLiveData<RepositoryResponse<User>> result = new MutableLiveData<>();
        MainRequest request = new MainRequest(mobileNum);
        service.getAllWeatherData(request).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("DataDailyForcast111", "" + response.body().results);
                if (response.isSuccessful() && response.body() != null) {
                    result.setValue(new RepositoryResponse<>(response.body()));

                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("DataDailyForcast", "" + t.getMessage());
                Log.d("DataDailyForcast111", "" + call.request().url());
                result.setValue(new RepositoryResponse<User>(t.getMessage(),
                        400));

            }

        });
        return result;
    }

    @Override
    public MutableLiveData<RepositoryResponse<Objects>> setRegistration(User userRegistration) {
        MutableLiveData<RepositoryResponse<Objects>> result = new MutableLiveData<>();

        service.setRegistration(userRegistration).enqueue(new Callback<Objects>() {

            @Override
            public void onResponse(Call<Objects> call, Response<Objects> response) {
                Log.d("DataDailyForcastRegistraton", "" + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    result.setValue(new RepositoryResponse<Objects>(response.body()));

                }
            }

            @Override
            public void onFailure(Call<Objects> call, Throwable t) {
                Log.d("DataDailyForcast", "" + t.getMessage());
                Log.d("DataDailyForcast111", "" + call.request().url());
                result.setValue(new RepositoryResponse<Objects>(t.getMessage(),
                        400));

            }

        });
        return result;
    }*/
}
