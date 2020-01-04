package com.example.dblog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dblog.adapters.BlogAdapter
import com.example.dblog.data.Blog
import com.example.dblog.viewmodels.BlogViewModel

class MainActivity : AppCompatActivity() {

    var blogViewModel:BlogViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val adapter : BlogAdapter = BlogAdapter()
        recyclerView.adapter = adapter

        //this references the viewmodel
        blogViewModel = ViewModelProviders.of(this).get(BlogViewModel::class.java)

        blogViewModel!!.getAllBlogs().observe(this, object : Observer<List<Blog>>{
            //updates and refreshes new list
            override fun onChanged(blogs : List<Blog>?) {
              //update recycler view
                //Toast.makeText(applicationContext, "it works!", Toast.LENGTH_LONG).show()
            adapter.setBlog(blogs!!)

            }

        })

    }
}
