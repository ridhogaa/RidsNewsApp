package com.ergea.ridsnewsapp.data.remote.api.model


import com.ergea.ridsnewsapp.model.News
import com.google.gson.annotations.SerializedName

data class GetNewsResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<Article>?
) {
    data class Article(
        @SerializedName("source")
        val source: Source,
        @SerializedName("author")
        val author: String?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("url")
        val url: String?,
        @SerializedName("urlToImage")
        val urlToImage: String?,
        @SerializedName("publishedAt")
        val publishedAt: String? ,
        @SerializedName("content")
        val content: String?
    ) {
        data class Source(
            @SerializedName("id")
            val id: String?,
            @SerializedName("name")
            val name: String?
        )
    }
}

fun GetNewsResponse.Article.mapToEntity() = News(
    this.source.mapToEntity(),
    this.author.orEmpty(),
    this.title.orEmpty(),
    this.description.orEmpty(),
    this.url.orEmpty(),
    this.urlToImage.orEmpty(),
    this.publishedAt.orEmpty(),
    this.content.orEmpty()
)

fun GetNewsResponse.Article.Source.mapToEntity() = News.Source(
    this.id.orEmpty(),
    this.name.orEmpty(),
)