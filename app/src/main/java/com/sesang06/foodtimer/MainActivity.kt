package com.sesang06.foodtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sesang06.foodtimer.main.MainItem
import com.sesang06.foodtimer.main.MainItemAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var mainItemRecylcerView: RecyclerView

    private val mainItemAdapter: MainItemAdapter by lazy {
        MainItemAdapter(
            this, listOf(
                MainItem(
                    "맛있는 음식",
                    10,
                    4
                )
            )
        )

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainItemRecylcerView = findViewById(R.id.rv_main)
        mainItemRecylcerView.adapter = mainItemAdapter
        mainItemRecylcerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mainItemAdapter.notifyDataSetChanged()
    }


}