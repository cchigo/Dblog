package com.example.dblog.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.dblog.data.Blog
import com.example.dblog.data.BlogRepository

class BlogViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: BlogRepository = BlogRepository(application)

    private var allBlogs: LiveData<List<Blog>> = repository.getAllBlogs()

    fun insert(blog: Blog) {
        repository.insert(blog)
    }
    fun update(blog: Blog) {
        repository.update(blog)
    }
    fun delete(blog: Blog){
        repository.delete(blog)
    }
    fun deleteAllBlogs(){
        repository.deleteAllBlogs()
    }
    fun getAllBlogs(): LiveData<List<Blog>>{
        return allBlogs
    }
}