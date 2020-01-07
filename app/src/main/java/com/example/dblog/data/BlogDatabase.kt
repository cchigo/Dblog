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
            blogDao?.insert(Blog("Travel", "How to travel alone", "Solo travel can be the ultimate in self-indulgence; you can rest when you want and pour it on when you’re feeling ambitious. Another benefit is that your mistakes are your own, and your triumphs all the more exciting. There’s no worrying that your insistence on trekking all the way across town to a museum that was closed ruined your partner’s day; it’s your own day to salvage or chalk up to a learning experience"))
            blogDao?.insert(Blog("Movies", "Money Heist Season 2. Let's Talk!", "MONEY HEIST season 2 set up a truly tantalising 3rd series which dropped on Netflix last month. Here’s a full recap of what happened in La Casa de Papel part 2."))

            }
    }

}