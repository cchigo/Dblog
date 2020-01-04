package com.example.dblog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.dblog.data.Blog
import com.example.dblog.viewmodels.BlogViewModel

class MainActivity : AppCompatActivity() {

    var blogViewModel:BlogViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blogViewModel = ViewModelProviders.of(this).get(BlogViewModel::class.java)

        blogViewModel!!.getAllBlogs().observe(this, object : Observer<List<Blog>>{
            override fun onChanged(t: List<Blog>?) {
              //update recycler view
                Toast.makeText(applicationContext, "it works!", Toast.LENGTH_LONG).show()
            }

        })

    }
}
