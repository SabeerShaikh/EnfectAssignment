package com.e.enfecassigmentkotlin.module.base;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.e.enfecassigmentkotlin.module.ui.viewmodel.MainViewModel;

import org.jetbrains.annotations.NotNull;


public class EnfecProducationViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;

    public EnfecProducationViewModelFactory(Application application) {
        mApplication = application;
    }

    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {

        if (modelClass == MainViewModel.class) {
            return (T) new MainViewModel(mApplication);
        }

        return super.create(modelClass);
    }
}
