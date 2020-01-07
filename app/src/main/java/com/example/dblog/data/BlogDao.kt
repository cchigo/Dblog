package com.example.dblog.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BlogDao {

    @Insert
    fun insert(blog: Blog)

    @Update
    fun update(blog: Blog)

    @Delete
    fun delete(blog: Blog)

    @Query("DELETE FROM blog_table")
    fun deleteAllBlogs()

    @Query("SELECT * FROM blog_table")
    fun getAllNotes(): LiveData<List<Blog>>
}