package com.sesang06.foodtimer.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sesang06.foodtimer.R

class MainItemAdapter(private val context: Context, private val items: List<MainItem>) :
    RecyclerView.Adapter<MainItemAdapter.MainItemViewHolder>() {

    class MainItemViewHolder(view: View) : RecyclerView.ViewHolder(
       view
    ) {
        val titleTextView: TextView = view.findViewById(R.id.tv_title_main)
        val timeTextView: TextView = view.findViewById(R.id.tv_time_main)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main, parent, false)
        return MainItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        items[position].let { item ->

            with(holder) {
                titleTextView.text = item.title
                timeTextView.text = "${item.minutes}:${item.seconds}"
            }
        }
    }

}