package com.e.enfecassigmentkotlin.module.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.e.enfecassigmentkotlin.domain.RepositoryResponse
import com.e.enfecassigmentkotlin.domain.model.PostData
import com.e.enfecassigmentkotlin.domain.model.UserData
import com.e.enfecassigmentkotlin.domain.repository.MainRepository
import com.e.enfecassigmentkotlin.module.base.EnfecProducationViewModel
import com.e.enfecassigmentkotlin.module.base.SPViewModelResponse
import java.util.*

class MainViewModel(application: Application) :
    EnfecProducationViewModel(application) {
    var repository: MainRepository

    fun userData(): LiveData<SPViewModelResponse<java.util.ArrayList<UserData>?>> {
        return Transformations.map<RepositoryResponse<java.util.ArrayList<UserData>?>,
                SPViewModelResponse<java.util.ArrayList<UserData>?>>(repository!!
            .userData) { repoResponse ->

            if (repoResponse.success) {
                return@map SPViewModelResponse(repoResponse.repositoryResponse)
            }
            SPViewModelResponse(repoResponse.failureMessage, repoResponse.code)
        }
    }

    fun postsData(): LiveData<SPViewModelResponse<java.util.ArrayList<PostData>?>> {
        return Transformations.map<RepositoryResponse<java.util.ArrayList<PostData>?>,
                SPViewModelResponse<java.util.ArrayList<PostData>?>>(repository!!
            .postsData) { repoResponse ->

            if (repoResponse.success) {
                return@map SPViewModelResponse(repoResponse.repositoryResponse)
            }
            SPViewModelResponse(repoResponse.failureMessage, repoResponse.code)
        }
    }




    init {
        repository = di.provideMainRepository()
    }
}
