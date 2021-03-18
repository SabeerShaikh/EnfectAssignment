package com.e.enfecassigmentkotlin.module.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.e.enfecassigmentkotlin.R
import com.e.enfecassigmentkotlin.databinding.ItemUserDataBinding
import com.e.enfecassigmentkotlin.domain.model.uimodel.UIModel
import java.util.*

class DataAdapter : RecyclerView.Adapter<DataAdapter.DataAdapterViewHolder>() {
    var uiModels = ArrayList<UIModel>()
    fun clearData() {
        uiModels.clear()
        notifyDataSetChanged()
    }

    fun appendData(list: ArrayList<UIModel>) {
        if (list.size > 0) {
            uiModels.addAll(list)
            Log.d("SBE---", "Data")
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        position: Int
    ): DataAdapterViewHolder {
        val binding: ItemUserDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_user_data, parent, false
        )
        return DataAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: DataAdapterViewHolder,
        position: Int
    ) {
        val uiModel = uiModels[position]
        holder.vbinding.setUiModel(uiModel)
        holder.vbinding.setCallback(this)
    }

    override fun getItemCount(): Int {
        return uiModels.size
    }

    inner class DataAdapterViewHolder(binding: ItemUserDataBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        var vbinding: ItemUserDataBinding

        init {
            vbinding = binding
            vbinding.setCallback(this@DataAdapter)
        }
    }
}