package com.sesang06.foodtimer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sesang06.foodtimer.main.MainItem
import com.sesang06.foodtimer.main.MainItemAdapter
import com.sesang06.foodtimer.timer.TimerActivity

class MainActivity : AppCompatActivity(), MainItemAdapter.ItemClickListener {

    private lateinit var mainItemRecylerView: RecyclerView

    private val mainItemAdapter: MainItemAdapter by lazy {
        MainItemAdapter(
            this, listOf(
                MainItem(
                    "맛있는 음식",
                    10,
                    4
                )
            )
        ).apply {
            setItemClickListener(this@MainActivity)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainItemRecylerView = findViewById(R.id.rv_main)
        mainItemRecylerView.adapter = mainItemAdapter
        mainItemRecylerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mainItemAdapter.notifyDataSetChanged()
    }


    override fun onItemClick(item: MainItem) {
        val intent = Intent(this, TimerActivity::class.java)
        startActivity(intent)
    }
}