package com.example.dblog

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dblog.adapters.BlogAdapter
import com.example.dblog.data.Blog
import com.example.dblog.viewmodels.BlogViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val ADD_BLOG_REQUEST = 1
        const val EDIT_BLOG_REQUEST = 2
    }
    private lateinit var blogViewModel:BlogViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //onClick listener tto launch add blog activity via floating action button to get input back
        buttonAddBlog.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v:View) {
                val intent = Intent(this@MainActivity, AddBlogActivity::class.java)
                startActivityForResult(intent, ADD_BLOG_REQUEST)
            }
        })


        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        val adapter : BlogAdapter = BlogAdapter()
        recyclerView.adapter = adapter

        //this references the viewmodel
        blogViewModel = ViewModelProviders.of(this).get(BlogViewModel::class.java)

        blogViewModel.getAllBlogs().observe(this, object:Observer<List<Blog>> {
            override fun onChanged(blogs:List<Blog>?) {
                adapter.setBlog(blogs!!)
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //inserts new post
        if (requestCode == ADD_BLOG_REQUEST && resultCode == Activity.RESULT_OK){
            val newBlog = Blog(
                data!!.getStringExtra(AddBlogActivity.EXTRA_CATEGORY),
                data.getStringExtra(AddBlogActivity.EXTRA_TITLE),
                data.getStringExtra(AddBlogActivity.EXTRA_CONTENT)
            )
            blogViewModel.insert(newBlog)

            Toast.makeText(this, "Your Article has bee posted", Toast.LENGTH_LONG).show()

        //edits the blog
//        else if (requestCode == EDIT_BLOG_REQUEST && resultCode == Activity.RESULT_OK){
//            val id = data?.getIntExtra(AddBlogActivity.EXTRA_ID, -1)
//
//            if (id == 1) {
//                Toast.makeText(this, "update failure", Toast.LENGTH_LONG).show()
//            }
//            val updateBlog = Blog(
//                data!!.getStringExtra(AddBlogActivity.EXTRA_CATEGORY),
//                data.getStringExtra(AddBlogActivity.EXTRA_TITLE),
//                data.getStringExtra(AddBlogActivity.EXTRA_CONTENT)
//            )
//            updateBlog.id = data.getIntExtra(AddBlogActivity.EXTRA_ID, -1)
//            blogViewModel!!.update(updateBlog)

        } else {

            Toast.makeText(this, "Article not saved", Toast.LENGTH_LONG).show()
        }
    }
}
