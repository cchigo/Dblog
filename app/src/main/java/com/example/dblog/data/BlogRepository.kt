package com.example.dblog.data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class BlogRepository(application: Application ) {

     var blogDao: BlogDao

    private var allBlogs: LiveData<List<Blog>>

    init {
        val database: BlogDatabase = BlogDatabase.getInstance(
            application.applicationContext
        )!!
        blogDao = database.blogDao()
        allBlogs = blogDao.getAllNotes()
    }
    fun insert(blog: Blog){
        val insertBlogAsyncTask = InsertBlogAsyncTask(blogDao).execute(blog)
    }
    fun update(blog: Blog){
    }
    fun delete(blog: Blog){
    }
    fun deleteAllNotes(blog: Blog){
    }
    fun getAllNotes(blog: Blog){
    }

    companion object{
        private class InsertBlogAsyncTask(blog: Blog) : AsyncTask<Blog, Unit, Unit>(){
            val blogDao: BlogDao

            override fun doInBackground(vararg p0: Blog?) {
                blogDao.insert(p0[0]!!)
            }
        }

    }

}