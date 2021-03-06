package com.example.dblog

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_add_blog.*
import kotlinx.android.synthetic.main.blog_item.*
import java.text.DateFormat
import java.util.*

class AddBlogActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    //intent extra keys
    companion object {
        const val EXTRA_ID = "com.example.dblog.EXTRA_ID"
        const val EXTRA_TITLE = "com.example.dblog.EXTRA_TITLE"
        const val EXTRA_CATEGORY = "com.example.dblog.EXTRA_CATEGORY"
        const val EXTRA_CONTENT = "com.example.dblog.EXTRA_CONTENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_blog)


        //to display close button
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        //setTitle("Write a new blogpost")

        if (intent.hasExtra(EXTRA_ID)) {
            title = "Edit Blog"
            edit_text_title.editText?.setText(intent.getStringExtra(EXTRA_TITLE))
            edit_text_category.editText?.setText(intent.getStringExtra(EXTRA_CATEGORY))
            edit_text_content.setText(intent.getStringExtra(EXTRA_CONTENT))
        } else {
            title = "Write New Blog"
        }

    }

    //specify menu for this activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_blog_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.post_note -> {
                postNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun postNote() {
        if (edit_text_category.editText?.text.toString().trim().isBlank() ||
            edit_text_title.editText?.text.toString().trim().isBlank() ||

            edit_text_content.text.toString().trim().isBlank()
        ) {

            Toast.makeText(this, "You can not send an empty blogpost!", Toast.LENGTH_LONG).show()
            return
        }
        val data = Intent().apply {
            putExtra(EXTRA_TITLE, edit_text_title.editText?.text.toString())
            putExtra(EXTRA_CATEGORY, edit_text_category.editText?.text.toString())
            putExtra(EXTRA_CONTENT, edit_text_content.text.toString())

            if (intent.getIntExtra(EXTRA_ID, -1) != 1) {
                putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
            }

        }
        setResult(Activity.RESULT_OK, data)
        finish()
    }


}
