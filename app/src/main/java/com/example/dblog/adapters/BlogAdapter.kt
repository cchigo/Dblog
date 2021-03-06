package com.example.dblog.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dblog.R
import com.example.dblog.data.Blog
import kotlinx.android.synthetic.main.blog_item.*
import kotlinx.android.synthetic.main.blog_item.view.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class BlogAdapter : RecyclerView.Adapter<BlogAdapter.BlogHolder>() {
    private var blogs: List<Blog> = ArrayList<Blog>()
    private var listener: OnItemClickListener? = null

    // private position : Int
    // private var listener: AdapterView.OnItemClickListener? = null
    //holds views in each recyclerview item

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.blog_item, parent, false)
        return BlogHolder(itemView)
    }

    //takes care of getting data from blog object into the views(title, category and content) of note holder
    override fun onBindViewHolder(holder: BlogHolder, position: Int) {
        val currentBlog: Blog = blogs[position]

        holder.textViewTitle.text = currentBlog.title
        holder.textViewCategory.text = currentBlog.category
        holder.textViewContent.text = currentBlog.content


        //adds current date to each blog
        val calendar = Calendar.getInstance()
        val currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime())


        holder.textViewDate.setText(currentDate)




    }

    fun getBlogAt(position: Int): Blog {
        return blogs[position]


    }

    //sets lists of notes in recycler view
    fun setBlog(blogs: List<Blog>) {
        this.blogs = blogs
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return blogs.size
    }

    inner class BlogHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick((blogs[position]))
                }

            }

        }


        var textViewTitle: TextView = itemView.text_view_title
        var textViewCategory: TextView = itemView.text_view_category
        var textViewContent: TextView = itemView.text_view_content
        var textViewDate: TextView = itemView.current_date


    }

    interface OnItemClickListener {
        fun onItemClick(blog: Blog)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


}