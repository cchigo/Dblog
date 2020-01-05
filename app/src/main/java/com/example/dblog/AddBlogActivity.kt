package com.example.dblog

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_blog.*

class AddBlogActivity : AppCompatActivity() {

    //intent extra keys
    companion object {
        const val EXTRA_ID = "com.example.dblog.EXTRA_ID"
        const val EXTRA_TITLE = "com.example.dblog.EXTRA_TITLE"
        const val EXTRA_CATEGORY = "com.example.dblog.EXTRA_CATEGORY"
        const val EXTRA_CONTENT = "com.example.dblog.EXTRA_CONTENT"
    }

//
//    lateinit var  editTextTitle: EditText
//    lateinit var  editTextCategory:  EditText
//    lateinit var  editTextContent: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_blog)

//        editTextTitle = edit_text_title
//        editTextCategory = edit_text_category
//        editTextContent = edit_text_content

        //to display close button
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        //setTitle("Write a new blogpost")

        if(intent.hasExtra(EXTRA_ID)){
            title = "Write a new blogpost"
            edit_text_title.setText(intent.getStringExtra(EXTRA_TITLE))
            edit_text_category.setText(intent.getStringExtra(EXTRA_CATEGORY))
            edit_text_content.setText(intent.getStringExtra(EXTRA_CONTENT))
        } else {
            title = "Add Note"
        }
    }
    //specify menu for this activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_blog_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.post_note -> {
                postNote()
                true
            }
            else ->  super.onOptionsItemSelected(item)
        }
    }


        private fun postNote(){
            if (edit_text_category.text.toString().trim().isBlank() ||
                edit_text_title.text.toString().trim().isBlank() ||

                edit_text_content.text.toString().trim().isBlank() ){

                Toast.makeText(this, "You can not send an empty blogpost!", Toast.LENGTH_LONG).show()
                return
            }
            val data = Intent().apply {
                putExtra(EXTRA_TITLE, edit_text_title.text.toString())
                putExtra(EXTRA_CATEGORY, edit_text_category.text.toString())
                putExtra(EXTRA_CONTENT, edit_text_content.text.toString())

                if (intent.getIntExtra(EXTRA_ID, -1) !=1){
                    putExtra(EXTRA_ID, intent.getIntExtra(EXTRA_ID, -1))
                }

            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }


    }









