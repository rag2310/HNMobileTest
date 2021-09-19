package com.rago.hnmobiletest.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Entidad para la tabla de EXCLUDE_ARTICLE en base de datos
@Entity(tableName = "EXCLUDE_ARTICLE")
data class ExcludeArticle(
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    @ColumnInfo(name = "STORY_ID")
    var storyId: Long
)