package com.example.huggingfacer.Services

import com.google.gson.annotations.SerializedName

data class HuggingFaceDailyPaper(
    val paper: Paper,
    val publishedAt: String,
    val title: String,
    val thumbnail: String,
    val numComments: Int,
    val submittedBy: SubmittedBy
) {
    data class Paper(
        val id: String,
        val authors: List<Author>,
        val publishedAt: String,
        val title: String,
        val summary: String,
        val upvotes: Int
    ) {
        data class Author(
            @SerializedName("_id") val id: String, // Maps the "_id" field to "id"
            val name: String,
            val hidden: Boolean
        )
    }

    data class SubmittedBy(
        val avatarUrl: String,
        val fullname: String,
        val name: String,
        val type: String,
        val isPro: Boolean,
        val isHf: Boolean,
        val isMod: Boolean
    )
}
