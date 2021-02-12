package com.sesang06.foodtimer.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sesang06.foodtimer.R

class MainItemAdapter(private val context: Context, private var items: List<MainItem>) :
    RecyclerView.Adapter<MainItemAdapter.MainItemViewHolder>() {

    private var listener: ItemClickListener? = null

    class MainItemViewHolder(view: View) : RecyclerView.ViewHolder(
       view
    ) {
        val titleTextView: TextView = view.findViewById(R.id.tv_title_main)
        val timeTextView: TextView = view.findViewById(R.id.tv_time_main)
        val thumbnailImageView: ImageView = view.findViewById(R.id.iv_thumbnail_main)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_main, parent, false)
        return MainItemViewHolder(view)
    }

    fun setItems(list: List<MainItem>) {
        this.items = list
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MainItemViewHolder, position: Int) {
        items[position].let { item ->

            with(holder) {
                titleTextView.text = item.title
                timeTextView.text = item.timeText
                itemView.setOnClickListener { listener?.onItemClick(item) }
               Glide
                        .with(context)
                        .load(item.thumbnail)
                        .centerCrop()
                        .transform(RoundedCorners(50))
                        .into(thumbnailImageView)
                       .clearOnDetach()

            }
        }
    }



    fun setItemClickListener(listener: ItemClickListener?) {
        this.listener = listener
    }

    interface ItemClickListener {

        fun onItemClick(item: MainItem)
    }

}