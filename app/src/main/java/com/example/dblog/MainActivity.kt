package com.example.dblog

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
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
    private lateinit var blogViewModel: BlogViewModel
    private lateinit var adapter: BlogAdapter
    private lateinit var  viewHolder: RecyclerView.ViewHolder


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

        adapter  = BlogAdapter()
        recyclerView.adapter = adapter

        //this references the viewmodel
        blogViewModel = ViewModelProviders.of(this).get(BlogViewModel::class.java)

        blogViewModel.getAllBlogs().observe(this, object:Observer<List<Blog>> {
            override fun onChanged(blogs:List<Blog>) {
                adapter.setBlog(blogs)
            }
        })
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                blogViewModel.delete(adapter.getBlogAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "Blog Deleted", Toast.LENGTH_SHORT).show()
            }
        }).attachToRecyclerView(recycler_view)

        adapter.setOnItemClickListener(object : BlogAdapter.OnItemClickListener{
            override fun onItemClick(blog: Blog) {
                Toast.makeText(applicationContext, "${blog.id}", Toast.LENGTH_SHORT).show()
                var intent = Intent(baseContext, AddBlogActivity::class.java)
                intent.putExtra(AddBlogActivity.EXTRA_ID, blog.id)
                intent.putExtra(AddBlogActivity.EXTRA_TITLE, blog.title)
                intent.putExtra(AddBlogActivity.EXTRA_CATEGORY, blog.category)
                intent.putExtra(AddBlogActivity.EXTRA_CONTENT, blog.content)

                startActivityForResult(intent, EDIT_BLOG_REQUEST)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_blogs -> {
                blogViewModel.deleteAllBlogs()
                Toast.makeText(this, "All notes deleted!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
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


        } else if(requestCode == EDIT_BLOG_REQUEST && resultCode == Activity.RESULT_OK){
            val id = data?.getIntExtra(AddBlogActivity.EXTRA_ID, 1)

//           if(id == -1){
//               Toast.makeText(this, "Could not update! Error!", Toast.LENGTH_SHORT).show()
//            }
            val updateBlog = Blog(
                data!!.getStringExtra(AddBlogActivity.EXTRA_CATEGORY),
                data.getStringExtra(AddBlogActivity.EXTRA_TITLE),
                data.getStringExtra(AddBlogActivity.EXTRA_CONTENT)
            )
            id?.let {
                updateBlog.id = it
            }
            Log.i("Blogid", "$id")
            Toast.makeText(applicationContext, "$id", Toast.LENGTH_SHORT).show()

            blogViewModel.update(updateBlog)




        }else {
            Toast.makeText(this, "Article not saved", Toast.LENGTH_LONG).show()
        }
    }




}
