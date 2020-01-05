package com.example.dblog.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "blog_table")
data class Blog(
    var category: String?,

    var title: String?,

    var content: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}