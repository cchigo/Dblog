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
        val insertBlogAsyncTask = InsertBlogAsyncTask(blog).execute(blog)
    }
    fun update(blog: Blog){
        val updateBlogAsyncTask = UpdateBlogAsyncTask(blog).execute()
    }
    fun delete(blog: Blog){
        val deleteBlogAsyncTask = DeleteBlogAsyncTask(blog).execute()

    }
    fun deleteAllBlogs(blog: Blog){
        val deleteBlogAsyncTask = DeleteBlogAsyncTask(blog).execute()
    }
    fun getAllBlogs(blog: Blog){
       val deleteAllBlogAsyncTask = DeleteAllBlogAsyncTask(blog).execute()
    }

    companion object{

        private class InsertBlogAsyncTask(blog: Blog) : AsyncTask<Blog, Unit, Unit>(){
            lateinit var blogDao: BlogDao

            override fun doInBackground(vararg p0: Blog?) {
                blogDao.insert(p0[0]!!)
            }
        }

        private class UpdateBlogAsyncTask(blog: Blog) : AsyncTask<Blog, Unit, Unit>(){
            lateinit var blogDao: BlogDao

            override fun doInBackground(vararg p0: Blog?) {
                blogDao.update(p0[0]!!)
            }
        }

        private class DeleteBlogAsyncTask(blog: Blog) : AsyncTask<Blog, Unit, Unit>(){
            lateinit var blogDao: BlogDao

            override fun doInBackground(vararg p0: Blog?) {
                blogDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllBlogAsyncTask(blog: Blog) : AsyncTask<Blog, Unit, Unit>(){
            lateinit var blogDao: BlogDao

            override fun doInBackground(vararg p0: Blog?) {
                blogDao.deleteAllBlogs(p0[0]!!)
            }
        }


    }

}