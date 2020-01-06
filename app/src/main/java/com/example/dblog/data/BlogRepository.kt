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
        val updateBlogAsyncTask = UpdateBlogAsyncTask(blogDao).execute(blog)
    }
    fun delete(blog: Blog){
        val deleteBlogAsyncTask = DeleteBlogAsyncTask(blogDao).execute(blog)

    }
    fun deleteAllBlogs(){
        val deleteAllBlogAsyncTask = DeleteAllBlogAsyncTask().execute()

    }
    fun getAllBlogs():LiveData<List<Blog>> {
        return allBlogs
    }



    private class InsertBlogAsyncTask(val blogDao: BlogDao) : AsyncTask<Blog, Unit, Unit>(){



        override fun doInBackground(vararg p0: Blog?) {
            blogDao.insert(p0[0]!!)
        }
    }

    private class UpdateBlogAsyncTask(val blogDao: BlogDao) : AsyncTask<Blog, Unit, Unit>(){


        override fun doInBackground(vararg p0: Blog?) {

            blogDao.update(p0[0]!!)
        }
    }

    private class DeleteBlogAsyncTask(val blogDao: BlogDao) : AsyncTask<Blog, Unit, Unit>(){


        override fun doInBackground(vararg p0: Blog?) {
            blogDao.delete(p0[0]!!)
        }
    }

    private class DeleteAllBlogAsyncTask() : AsyncTask<Blog, Unit, Unit>(){
        lateinit var blogDao: BlogDao

        override fun doInBackground(vararg p0: Blog?) {
            blogDao.deleteAllBlogs()
        }
    }
}