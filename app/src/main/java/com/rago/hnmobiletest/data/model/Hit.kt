package com.rago.hnmobiletest.data.model

import com.google.gson.annotations.SerializedName


//Model de los articulos de noticias consultados en el api
data class Hit(
    @SerializedName("author")
    var author: String?,
    @SerializedName("story_id")
    var storyId: Long?,
    @SerializedName("story_title")
    var storyTitle: String?,
    @SerializedName("story_url")
    var story_url: String?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("title")
    var title: String?
)
