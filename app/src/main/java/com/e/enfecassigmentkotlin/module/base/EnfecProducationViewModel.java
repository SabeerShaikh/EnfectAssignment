package com.e.enfecassigmentkotlin.module.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.e.enfecassigmentkotlin.config.DI;
import com.e.enfecassigmentkotlin.config.EnfecProducationDI;

public class EnfecProducationViewModel extends AndroidViewModel {

    protected static DI di;
    protected static EnfecProducationViewModelFactory vmCommonFactory;
    protected Application application;

    public EnfecProducationViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        if (di == null) {
            di = new EnfecProducationDI(application);
            vmCommonFactory = di.provideViewModelFactory();
        }
    }
}
