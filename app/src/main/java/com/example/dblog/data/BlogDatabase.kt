package com.example.dblog.data

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Blog::class], version = 4, exportSchema = false)
abstract class BlogDatabase: RoomDatabase() {

    abstract fun blogDao(): BlogDao

    companion object {
        private var instance: BlogDatabase? = null


        fun getInstance(context: Context): BlogDatabase? {
            if (instance == null) {
                synchronized(BlogDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BlogDatabase::class.java, "note_database"
                    )
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }

    }
    class PopulateDbAsyncTask(db: BlogDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val blogDao = db?.blogDao()

        override fun doInBackground(vararg params: Unit?) {
//            blogDao?.insert(Blog("category1", "title1", "content1"))
//            blogDao?.insert(Blog("category2", "title2", "content2"))
//            blogDao?.insert(Blog("category3", "title3", "content3"))
//            blogDao?.insert(Blog("category4", "title4", "content4"))
//            blogDao?.insert(Blog("category5", "title5", "content5"))
//            blogDao?.insert(Blog("category6", "title6", "content6"))
            }
    }

}