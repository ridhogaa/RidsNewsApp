package com.ergea.ridsnewsapp.data.remote.api.model


import com.ergea.ridsnewsapp.model.Source
import com.google.gson.annotations.SerializedName

data class GetSourceResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("sources")
    val sources: List<Source>?
) {
    data class Source(
        @SerializedName("id")
        val id: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("description")
        val description: String?,
        @SerializedName("url")
        val url: String?,
        @SerializedName("category")
        val category: String?,
        @SerializedName("language")
        val language: String?,
        @SerializedName("country")
        val country: String?
    )
}

fun GetSourceResponse.Source.mapToEntity() = Source(
    this.id,
    this.name,
    this.description,
    this.url,
    this.category,
    this.language,
    this.country,
)