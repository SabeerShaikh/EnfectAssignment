package com.enfecassignment.module.ui.activity

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.enfecassigmentkotlin.R
import com.e.enfecassigmentkotlin.config.DI
import com.e.enfecassigmentkotlin.config.EnfecProducationDI
import com.e.enfecassigmentkotlin.databinding.ActivityMainBinding
import com.e.enfecassigmentkotlin.domain.model.PostData
import com.e.enfecassigmentkotlin.domain.model.UserData
import com.e.enfecassigmentkotlin.domain.model.uimodel.UIModel
import com.e.enfecassigmentkotlin.module.ui.adapter.DataAdapter
import com.e.enfecassigmentkotlin.module.ui.viewmodel.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var vBinding: ActivityMainBinding
    private var viewModel: MainViewModel? = null
    private var di: DI? = null
    private var uimodelList: ArrayList<UIModel> = ArrayList<UIModel>()

    private var userDataList: MutableList<UserData> =
        Collections.synchronizedList(java.util.ArrayList<UserData>())
    private var postDataList: MutableList<PostData> =
        Collections.synchronizedList(java.util.ArrayList<PostData>())
    private var adapter: DataAdapter? = null
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        di = EnfecProducationDI(Application())
        vBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        vBinding.callback = this
        viewModel = ViewModelProvider(
            this,
            (di as EnfecProducationDI).provideViewModelFactory()
        ).get<MainViewModel>(MainViewModel::class.java)
        vBinding.rvData.layoutManager = LinearLayoutManager(
            applicationContext,
            RecyclerView.VERTICAL, false
        )
        vBinding.rvData.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        adapter = DataAdapter()
        vBinding.rvData.adapter = adapter
        vBinding.pbMain.visibility = View.VISIBLE
        loadPostData()
        loadUserData()
        handler.postDelayed(Runnable { updateModel() }, 2000)
    }

    private fun loadPostData() {

        viewModel?.postsData()?.observe(this@MainActivity, Observer {
            if (it.success) {
                it.response?.let { it1 -> postDataList.addAll(it1) }


            } else {
                toast("Post Data is not loaded")
                vBinding.pbMain.visibility = View.GONE

            }
        })

    }

    private fun loadUserData() {
        viewModel?.userData()?.observe(this, Observer {
            Log.d("Data", "----!!!" + it.response?.size)

            if (it.success) {
                it.response?.let { it1 -> userDataList.addAll(it1) }

            } else {
                toast("User Data is not loaded")
                vBinding.pbMain.visibility = View.GONE

            }

        })


    }

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    fun updateModel() {
        if (!Objects.isNull(userDataList) && !Objects.isNull(postDataList)) {
            for (items in userDataList) {
                val uiModel = UIModel()
                uiModel.id = items.id
                uiModel.company_name = items.company.name
                if (uimodelList.contains(uiModel)) {
                    Log.d("Data", "Already exist");
                } else {
                    uimodelList.add(uiModel)
                }
            }
            for (i in 0 until postDataList.size - 1) {

                if (uimodelList.size > i) {
                    if (postDataList[i].id == uimodelList[i].id) {
                        uimodelList[i].body = postDataList[i].body
                        uimodelList[i].title = postDataList[i].title
                    }
                }

            }
            adapter?.appendData(uimodelList)
            vBinding.pbMain.visibility = View.GONE

        } else {
            vBinding.pbMain.visibility = View.GONE

        }

    }
}