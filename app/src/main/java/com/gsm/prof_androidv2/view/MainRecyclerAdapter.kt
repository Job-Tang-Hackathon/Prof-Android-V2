package com.gsm.prof_androidv2.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gsm.prof_androidv2.R

class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainRecyclerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem =
            layoutInflater.inflate(R.layout.project_recycler_view_item, parent, false)

        return MainRecyclerViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MainRecyclerViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }
}

class MainRecyclerViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}